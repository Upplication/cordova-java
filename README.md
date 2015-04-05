# Cordova Java API

Cordova Java Client to automate tasks

## Examples Usages


```java
// get cordova cli
Cordova cordova = new Cordova("path to node", "path to cordova");
CordovaCLI cli = cordova.getCLI();
```

```java
cli.create(path, "dirName", "com.upplication.sample", "Sample");
// creat a project with cordova default name
cli.create(path, "dirName", "com.upplication.sample"); 
// create a project with cordova default name and bundleid
cli.create(path, "dirName"); 
```

```java
// when you create a project with the cli.create, you get an instance of the current cordovaProject
CordovaProject cordova = cli.create(path, "dirName", "com.upplication.sample", "Sample");
// you can get the CordovaProject object from an existing cordova project
CordovaProject cordova = Cordova.getProject(new File("/home/user/projects/existing-project"));
```

```java
// add
cordova.platform().add(Platform.IOs); // TODO: maybe addIos();
cordova.platform().add(Platform.Android);
// remove
cordova.platform().remove(Platform.Android); // TODO: maybe removeAndroid(); or platform().remove().android();
// list
List<Platform> platforms = cordova.platform().list();
```

```java
cordova.build();
cordova.build(Platform.IOs);
cordova.prepare();
cordova.prepare(Platform.IOs);
cordova.compile();
cordova.compile(Platform.IOs);
```

```java
cordova.emulate(Platform.ANDROID);
cordova.run(Platform.ANDROID);
```

```java
cordova.plugin().add("org.apache.cordova.console");
cordova.plugin().add("org.apache.cordova.console", "org.apache.cordova.device");
// you can set the version
cordova.plugin().add("org.apache.cordova.console@0.2.1");
cordova.plugin().add("org.apache.cordova.console@latest");
// you can add custom github repo
cordova.plugin().add("https://github.com/apache/cordova-plugin-console.git");
// you can add custom github repo with version
cordova.plugin().add("https://github.com/apache/cordova-plugin-console.git@latest");
// you can add paths
cordova.plugin().add(new File("../my_plugin_dir"));
// add plugins with vars
cordova.plugin().add("org.apache.cordova.console", Plugin.Var.add("variable"));
cordova.plugin().add("org.apache.cordova.console", Plugin.Var.add("variable"), Plugin.Var.add("anotherOne"));
// read installed plugin
List<Plugin> plugins = cordova.plugin().list();
// find plugin with terms bar and code as case-insensitive substrings
List<Plugin> plugins = cordova.plugin().search("bar", "code");
```

And the best: Customize Cordova

```java
// basic info
cordova.config().setName();
cordova.config().setVersion(1,0,0);
// if you want custom version for app
cordova.config().setVersion(Version.create()
    .version("1.0.0")
    .iosCfBundleVersion("3.3.3")
    .androidVersionCode(7));

cordova.config().setDescription("description");
// author
cordova.config().author().setEmail("adad");
cordova.config().author().setHref("adad");
cordova.config().author().setName("name");
// access
cordova.config().access().add("*");
// with launch-external
cordova.config().access().add("*", "yes");
cordova.config().access().add(Access.create().origin("*").value("disable").subdomains(true));
cordova.config().preferences().add("name", "value");
// icon
cordova.config().icon().add("src/img/icon.png");
cordova.config().icon().add(Icon.create().src("src/img").height(100).width(100).density("zsdad"));
// platform icon
cordova.config().platform(Platform.Android).icon().add("src/img/android/icon.png")
cordova.config().platform(Platform.Android).icon().add(Icon.create().src("src/img").density("ldpi"));
// platform splash
cordova.config().platform(Platform.Android).splash().add(Splash.create().src("dest/splash.png").density("low"));
cordova.config().platform(Platform.IOs).splash().add(Splash.create().src("dest/splash.png").width(320).height(100));
// platform preferences
cordova.config().platform(Platform.Android).preferences().add("name", "value");
```

Read data for the current project

```java
String name = cordova.config().getName();
String version = cordova.config().getVersion();
String description = cordova.config().getDescription();

String email = cordova.config().author().getEmail();
String href = cordova.config().author().getHref();
String name = cordova.config().author().getName();

List<Icon> icons = cordova.config().icon().getAll();
List<Access> access = cordova.config().access().getAll();
List<Preferences> preferences = cordova.config().preferences().getAll();
List<Icon> icons = cordova.config().platform(Platform.Android).icon().getAll();
List<Splash> splash = cordova.config().platform(Platform.IOs).splash().getAll();

// I want to add a new icon in the recently changed config.xml

File file = cordova.getProject();
Path dest = file.toPath().resolve(icon.get(0).getSrc());
Files.copy(src, dest);

// or if you are already changing your config.xml
Icon icon = Icon.create().src("src/img").height(100).width(100);
cordova.config().icon().add(icon);
Files.copy(src, cordova.getProject().toPath().resolve(icon.getSrc()));
```

