# Cordova Java API

Cordova Java Client to automate tasks

## Examples Usages


```java 
// get cordova cli with a custom path installation
CordovaCLI cli = new CordovaCLI("pat to cordova")
```

```java
// get cordova cli instaled with npm cordova -g or with cordova setted in the path
CordovaCLI cli = Cordova.getCLI();
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
CordovaProject cordova = Cordova.getProject(new File("/home/documents/existing-project"));
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
cordova.plugin().add("../my_plugin_dir"); or cordova.plugin().add(new File("../my_plugin_dir"));
// add plugins with vars
cordova.plugin().add("org.apache.cordova.console", Plugin.Var.add("variable"));
cordova.plugin().add("org.apache.cordova.console", Plugin.Var.add("variable"), Plugin.Var.add("anotherOne"));
```

And the best: Customize Cordova

```java
// basic info
cordova.config().setName();
cordova.config().setVersion(1,0,0);
cordova.config().setDescription("description");
// author
cordova.config().getAuthor().setEmail("adad");
cordova.config().getAuthor().setHref("adad");
cordova.config().getAuthor().setName("name");
// access
cordova.config().getAccess().add("*");
// with launch-external
cordova.config().getAccess().add("*", "yes");
cordova.config().getAccess().add(Access.create().origin("*").value("disable").subdomains(true));
cordova.config().getPreferences().add("name", "value");
// icon
cordova.config().icon().add("src/img/icon.png");
cordova.config().icon().add(Icon.create().src("src/img").height(100).width(100).density("zsdad"));
// platform icon
cordova.config().platform("android").icon().add("src/img/android/icon.png")
cordova.config().platform("android").icon().add(Icon.create().src("src/img").density("ldpi"));
// platform splash
cordova.config().platform("android").splash().add(Splash.create().src("dest/splash.png").density("low"));
cordova.config().platform("ios").splash().add(Splash.create().src("dest/splash.png").width(320).height(100));
```

