package com.upplication.cordova.util;

import com.upplication.cordova.*;
import java.io.IOException;
import java.util.List;

/**
 * Processes the cordova config.xml file
 */
public interface IConfigProcessor {

    /**
     * Sets the version of the application in the config.xml file
     *
     * @param version            Version
     * @param iosCfBundleVersion ios Version, String, optional can be null
     * @param androidVersionCode android Version, Integer, optional can be null
     * @throws IOException if the operation cant be performed
     */
    void setVersion(String version, String iosCfBundleVersion, Integer androidVersionCode) throws IOException;

    /**
     * Get the version of the application in the config.xml file
     *
     * @return Version never null
     * @throws IOException if the version cant be retrieved
     */
    Version getVersion() throws IOException;

    /**
     * Sets the name of the application in the config.xml file
     *
     * @param name          String, mandatory not null
     * @throws IOException if the operation cant be performed
     */
    void setName(String name) throws IOException;

    /**
     * Get the name of the application in the config.xml file
     *
     * @return the name never null
     * @throws IOException if the name cant be retrieved
     */
    String getName() throws IOException;

    /**
     * Sets the description of the application in the config.xml file
     *
     * @param description   String, mandatory not null
     * @throws IOException if the operation cant be performed
     */
    void setDescription(String description) throws IOException;

    /**
     * Get the description of the application in the config.xml file
     *
     * @return the description never null
     * @throws IOException if the description cant be retrieved
     */
    String getDescription() throws IOException;

    /**
     * Sets the author of the application in the config.xml file
     *
     * @param authorName String, the author name to set
     * @throws IOException if the operation cant be performed
     */
    void setAuthorName(String authorName) throws IOException;

    /**
     * Get the author name of the application in the config.xml file
     *
     * @return the author name as String never null
     * @throws IOException if the author name cant be retrieved
     */
    String getAuthorName() throws IOException;

    /**
     * Sets the author email of the application in the config.xml file
     *
     * @param authorEmail String the author email
     * @throws IOException if the operation cant be performed
     */
    void setAuthorEmail(String authorEmail) throws IOException;

    /**
     * Get the author email of the application in the config.xml file
     *
     * @return the author email as String never null
     * @throws IOException if the author email cant be retrieved
     */
    String getAuthorEmail() throws IOException;

    /**
     * Sets the author href of the application in the config.xml file
     *
     * @param authorHref String the author href
     * @throws IOException if the operation cant be performed
     */
    void setAuthorHref(String authorHref) throws IOException;

    /**
     * Get the author href of the application in the config.xml file
     *
     * @return the author href as String never null
     * @throws IOException if the author href cant be retrieved
     */
    String getAuthorHref() throws IOException;

    /**
     * Add a new access element in the config.xml
     *
     * @param accessOrigin String access origin to allow
     * @param launchExternal yes or no to allow or not the launch external of the url
     * @param subdomains String subdomains allowed
     * @throws IOException if the operation cant be performed
     */
    void addAccess(String accessOrigin, String launchExternal, String subdomains) throws IOException;

    /**
     * Get the list of access allowed in the config.xml
     *
     * @return List Access never null
     * @throws IOException if the list of access cant be retrieved
     */
    List<Access> getAccess() throws IOException;

    /**
     * Add a new allow-element element in the config.xml
     *
     * @param href String href to allow
     * @throws IOException if the operation cant be performed
     */
    void addAllowNavigation(String href) throws IOException;

    /**
     * Get the list of allow-navigation allowed in the config.xml
     *
     * @return List AllowNavigation never null
     * @throws IOException if the list of allow-navigation cant be retrieved
     */
    List<AllowNavigation> getAllowNavigation() throws IOException;

    /**
     * Add a new preference element in the concrete platform with a name and a value attrs
     *
     * @param platform String platform: ios, android ...
     * @param name String attr name
     * @param value String attr value
     * @throws IOException if the operation cant be performed
     */
    void addPreference(String platform, String name, String value) throws IOException;
    /**
     * Get the list of preferences allowed in the config.xml for a concrete platform
     *
     * @param platform String with the platform to filter against it
     * @return List Preference never null
     * @throws IOException if the list of preference cant be retrieved
     */
    List<Preference> getPreferences(String platform) throws IOException;

    /**
     * Add a new icon element in the concrete platform with a src, width, height and a density
     *
     * @param platform String with the platform to filter against it
     * @param src String the icon relative path
     * @param width Integer, optional the width in pixels
     * @param height Integer, optional the width in pixels
     * @param density String, optional the density
     * @throws IOException if the operation cant be performed
     */
    void addIcon(String platform, String src, Integer width, Integer height, String density) throws IOException;

    /**
     * Get the list of icons added in the config.xml for a concrete platform
     *
     * @param platform String with the platform to filter against it
     * @return List Icon never null
     * @throws IOException if the list of icon cant be retrieved
     */
    List<Icon> getIcons(String platform) throws IOException;

    /**
     * Add a new Splash element in the concrete platform with a src, widht, height and density
     *
     * @param platform String with the platform to filter against it
     * @param src String the icon relative path
     * @param width Integer, optional the width in pixels
     * @param height Integer, optional the width in pixels
     * @param density String, optional the density
     * @throws IOException if the operation cant be performed
     */
    void addSplash(String platform, String src, Integer width, Integer height, String density) throws IOException;

    /**
     * Get the list of splash element in the config.xml for a concrete platform
     *
     * @param platform String with the platform to filter against it
     * @return List Splash never null
     * @throws IOException if the list of splash element cant be retrieved
     */
    List<Splash> getSplashs(String platform) throws IOException;


    /**
     * Add a new edit-config element in the concrete platform (or general if null)
     *
     * @param platform String platform: ios, android ...
     * @param file String The file to be modified, and the path relative to the root of the Cordova project.
     * @param target String An XPath selector referencing the target element to make attribute modifications to
     * @param mode String The mode that determines what type of attribute modifications will be made.
     * @param content String The XML to edit
     * @throws IOException if the content is not a valid XML and cant be saved
     */
    void addEditConfig(String platform, String file, String target, String mode, String content) throws IOException;

    /**
     * Get the list of edit-config allowed in the config.xml for a concrete platform
     *
     * @param platform String platform to find
     * @return List EditConfig never null
     * @throws IOException if the list of edit-config element cant be retrieved
     */
    List<EditConfig> getEditConfig(String platform) throws IOException;

    /**
     * Add a new config-file element in the concrete platform (or general if null)
     *
     * @param platform String platform: ios, android ...
     * @param target String The file to be modified, and the path relative to the root of the Cordova project. If the specified file does not exist, the tool ignores the configuration change and continues installation.
     * @param parent String An XPath selector referencing the parent of the elements to be added to the config file. If you use absolute selectors, you can use a wildcard (*) to specify the root element
     * @param after String A prioritized list of accepted siblings after which to add the XML snippet.
     * @param content String The XML to add
     * @throws IOException if the content is not a valid XML and cant be saved
     */
    void addConfigFile(String platform, String target, String parent, String after, String content) throws IOException;

    /**
     * Get the list of config-file allowed in the config.xml for a concrete platform
     *
     * @param platform String platform to find
     * @return List EditConfig never null
     * @throws IOException if the list of config-file element cant be retrieved
     */
    List<ConfigFile> getConfigFile(String platform) throws IOException;
    /**
     * Add some custom fragments of XML tag.
     * Some plugins add their custom tags to the config.xml so we need to support custom fragments.
     *
     * TODO: use jackson-dataformat-xml or jaxb to append custom Java Object to the config.xml and retrieve it
     *
     * @param xml String mandatory tag
     * @throws IOException if the xml is invalid and cant be parsed
     */
    void add(String xml) throws IOException;
}
