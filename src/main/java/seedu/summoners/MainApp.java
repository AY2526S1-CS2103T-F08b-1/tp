package seedu.summoners;

import java.io.IOException;
import java.nio.file.Path;
import java.util.Optional;
import java.util.logging.Logger;

import javafx.application.Application;
import javafx.stage.Stage;
import seedu.summoners.commons.core.Config;
import seedu.summoners.commons.core.LogsCenter;
import seedu.summoners.commons.core.Version;
import seedu.summoners.commons.exceptions.DataLoadingException;
import seedu.summoners.commons.util.ConfigUtil;
import seedu.summoners.commons.util.StringUtil;
import seedu.summoners.logic.Logic;
import seedu.summoners.logic.LogicManager;
import seedu.summoners.model.SummonersBook;
import seedu.summoners.model.Model;
import seedu.summoners.model.ModelManager;
import seedu.summoners.model.ReadOnlySummonersBook;
import seedu.summoners.model.ReadOnlyUserPrefs;
import seedu.summoners.model.UserPrefs;
import seedu.summoners.model.util.SampleDataUtil;
import seedu.summoners.storage.SummonersBookStorage;
import seedu.summoners.storage.JsonSummonersBookStorage;
import seedu.summoners.storage.JsonUserPrefsStorage;
import seedu.summoners.storage.Storage;
import seedu.summoners.storage.StorageManager;
import seedu.summoners.storage.UserPrefsStorage;
import seedu.summoners.ui.Ui;
import seedu.summoners.ui.UiManager;

/**
 * Runs the application.
 */
public class MainApp extends Application {

    public static final Version VERSION = new Version(1, 4, 0, true);

    private static final Logger logger = LogsCenter.getLogger(MainApp.class);

    protected Ui ui;
    protected Logic logic;
    protected Storage storage;
    protected Model model;
    protected Config config;

    @Override
    public void init() throws Exception {
        logger.info("=============================[ Initializing SummonersBook ]===========================");
        super.init();

        AppParameters appParameters = AppParameters.parse(getParameters());
        config = initConfig(appParameters.getConfigPath());
        initLogging(config);

        UserPrefsStorage userPrefsStorage = new JsonUserPrefsStorage(config.getUserPrefsFilePath());
        UserPrefs userPrefs = initPrefs(userPrefsStorage);
        SummonersBookStorage summonersBookStorage = new JsonSummonersBookStorage(userPrefs.getSummonersBookFilePath());
        storage = new StorageManager(summonersBookStorage, userPrefsStorage);

        model = initModelManager(storage, userPrefs);

        logic = new LogicManager(model, storage);

        ui = new UiManager(logic);
    }

    /**
     * Returns a {@code ModelManager} with the data from {@code storage}'s summoners book and {@code userPrefs}. <br>
     * The data from the sample summoners book will be used instead if {@code storage}'s summoners book is not found,
     * or an empty summoners book will be used instead if errors occur when reading {@code storage}'s summoners book.
     */
    protected Model initModelManager(Storage storage, ReadOnlyUserPrefs userPrefs) {
        logger.info("Using data file : " + storage.getSummonersBookFilePath());

        Optional<ReadOnlySummonersBook> summonersBookOptional;
        ReadOnlySummonersBook initialData;
        try {
            summonersBookOptional = storage.readSummonersBook();
            if (!summonersBookOptional.isPresent()) {
                logger.info("Creating a new data file " + storage.getSummonersBookFilePath()
                        + " populated with a sample SummonersBook.");
            }
            initialData = summonersBookOptional.orElseGet(SampleDataUtil::getSampleSummonersBook);
        } catch (DataLoadingException e) {
            logger.warning("Data file at " + storage.getSummonersBookFilePath() + " could not be loaded."
                    + " Will be starting with an empty SummonersBook.");
            initialData = new SummonersBook();
        } catch (Exception e) {
            logger.warning("Data file at " + storage.getSummonersBookFilePath()
                    + " contains invalid data (e.g., player in multiple teams, duplicate roles/champions)."
                    + " Will be starting with an empty SummonersBook. Error: " + e.getMessage());
            initialData = new SummonersBook();
        }

        return new ModelManager(initialData, userPrefs);
    }

    private void initLogging(Config config) {
        LogsCenter.init(config);
    }

    /**
     * Returns a {@code Config} using the file at {@code configFilePath}. <br>
     * The default file path {@code Config#DEFAULT_CONFIG_FILE} will be used instead
     * if {@code configFilePath} is null.
     */
    protected Config initConfig(Path configFilePath) {
        Config initializedConfig;
        Path configFilePathUsed;

        configFilePathUsed = Config.DEFAULT_CONFIG_FILE;

        if (configFilePath != null) {
            logger.info("Custom Config file specified " + configFilePath);
            configFilePathUsed = configFilePath;
        }

        logger.info("Using config file : " + configFilePathUsed);

        try {
            Optional<Config> configOptional = ConfigUtil.readConfig(configFilePathUsed);
            if (!configOptional.isPresent()) {
                logger.info("Creating new config file " + configFilePathUsed);
            }
            initializedConfig = configOptional.orElse(new Config());
        } catch (DataLoadingException e) {
            logger.warning("Config file at " + configFilePathUsed + " could not be loaded."
                    + " Using default config properties.");
            initializedConfig = new Config();
        }

        //Update config file in case it was missing to begin with or there are new/unused fields
        try {
            ConfigUtil.saveConfig(initializedConfig, configFilePathUsed);
        } catch (IOException e) {
            logger.warning("Failed to save config file : " + StringUtil.getDetails(e));
        }
        return initializedConfig;
    }

    /**
     * Returns a {@code UserPrefs} using the file at {@code storage}'s user prefs file path,
     * or a new {@code UserPrefs} with default configuration if errors occur when
     * reading from the file.
     */
    protected UserPrefs initPrefs(UserPrefsStorage storage) {
        Path prefsFilePath = storage.getUserPrefsFilePath();
        logger.info("Using preference file : " + prefsFilePath);

        UserPrefs initializedPrefs;
        try {
            Optional<UserPrefs> prefsOptional = storage.readUserPrefs();
            if (!prefsOptional.isPresent()) {
                logger.info("Creating new preference file " + prefsFilePath);
            }
            initializedPrefs = prefsOptional.orElse(new UserPrefs());
        } catch (DataLoadingException e) {
            logger.warning("Preference file at " + prefsFilePath + " could not be loaded."
                    + " Using default preferences.");
            initializedPrefs = new UserPrefs();
        }

        //Update prefs file in case it was missing to begin with or there are new/unused fields
        try {
            storage.saveUserPrefs(initializedPrefs);
        } catch (IOException e) {
            logger.warning("Failed to save config file : " + StringUtil.getDetails(e));
        }

        return initializedPrefs;
    }

    @Override
    public void start(Stage primaryStage) {
        logger.info("Starting SummonersBook " + MainApp.VERSION);
        ui.start(primaryStage);
    }

    @Override
    public void stop() {
        logger.info("============================ [ Stopping SummonersBook ] =============================");
        try {
            storage.saveUserPrefs(model.getUserPrefs());
        } catch (IOException e) {
            logger.severe("Failed to save preferences " + StringUtil.getDetails(e));
        }
    }
}
