# Cordova Java API

Cordova Java Client to automate tasks.


[![Build Status](https://travis-ci.org/Upplication/cordova-java.svg?branch=master)](https://travis-ci.org/Upplication/cordova-java/builds) [![Coverage Status](https://coveralls.io/repos/github/Upplication/cordova-java/badge.svg?branch=master)](https://coveralls.io/github/Upplication/cordova-java?branch=master) [![Maven Central](https://maven-badges.herokuapp.com/maven-central/com.upplication/cordova-java/badge.svg)](https://maven-badges.herokuapp.com/maven-central/com.upplication/cordova-java)

## Getting started

### Download from Maven Central

TODO

```XML
<dependency>
	<groupId>com.upplication</groupId>
	<artifactId>cordova-java</artifactId>
	<version>1.0.0</version>
</dependency>
```

## How to contribute

Clone the github repository:

```java
git clone https://github.com/Upplication/cordova.java.git
```

To run the tests:

First, you must copy the file `src/test/resources/cordova-sample.properties` and paste in the same directory with the name cordova.properties. In your copy you must edit all the keys:

```
node_path = your node path or empty if you have added to your user PATH
cordova_path = your cordova path or null if you have added to your user PATH
```

Thats all, now you can run the test with the command: `mvn test` or/and `mvn test -Pintegration-tests`

## Features:

* Cordova Command Line Interface with a nice dsl API
* Cordova Command Line Interface for new projects and existing projects
* API to customize the config.xml with all the options and platform specific configurations

## Roadmap:

* Tests and Integrations test with Travis and Coveralls
* Full config.xml API implementation
* Easy version naming: cordova-java 6.1.1 works for cordova 6.1.1


## Examples Usages

```java
// create a new cordova instance ready to use
Cordova cordova = new Cordova("path to node", "path to cordova");
// or if its cordova already on the path
Cordova cordova = new Cordova();
```

```java
// get cordova cli
CordovaCLI cli = cordova.getCLI();
```

```java
// create a project
cli.create(path, "dirName", "com.upplication.sample", "Sample");
// create a project with cordova default name
cli.create(path, "dirName", "com.upplication.sample"); 
// create a project with cordova default name and bundleid
cli.create(path, "dirName"); 
```

```java
// when you create a project with the cli.create, you get an instance of the current cordovaProject
CordovaProject cordovaProject = cli.create(path, "dirName", "com.upplication.sample", "Sample");
// you can get the CordovaProject object from an existing cordova project
CordovaProject cordovaProject = cordova.getProject(new File("/home/user/projects/existing-project"));
```

```java
// add
cordovaProject.platform().add(Platform.IOs);
cordovaProject.platform().add(Platform.Android);
// remove
cordovaProject.platform().remove(Platform.Android);
// list
List<Platform> platforms = cordovaProject.platform().list();
```

```java
cordovaProject.build();
cordovaProject.build(Platform.IOs);
cordovaProject.prepare();
cordovaProject.prepare(Platform.IOs);
cordovaProject.compile();
cordovaProject.compile(Platform.IOs);
```

```java
cordovaProject.emulate(Platform.ANDROID);
cordovaProject.run(Platform.ANDROID);
```

```java
cordovaProject.plugin().add("org.apache.cordova.console");
cordovaProject.plugin().add("org.apache.cordova.console", "org.apache.cordova.device");
// you can set the version
cordovaProject.plugin().add("org.apache.cordova.console@0.2.1");
cordovaProject.plugin().add("org.apache.cordova.console@latest");
// you can add custom github repo
cordovaProject.plugin().add("https://github.com/apache/cordova-plugin-console.git");
// you can add custom github repo with version
cordovaProject.plugin().add("https://github.com/apache/cordova-plugin-console.git@latest");
// you can add paths
cordovaProject.plugin().add(new File("../my_plugin_dir"));
// add plugins with vars
cordovaProject.plugin().add("org.apache.cordova.console", Plugin.Var.add("variable"));
cordovaProject.plugin().add("org.apache.cordova.console", Plugin.Var.add("variable"), Plugin.Var.add("anotherOne"));
// read installed plugin
List<Plugin> plugins = cordovaProject.plugin().list();
// find plugin with terms bar and code as case-insensitive substrings
List<Plugin> plugins = cordovaProject.plugin().search("bar", "code");
```

And the best: Customize Cordova

```java
// basic info
cordovaProject.config().setName("name");
cordovaProject.config().setVersion(1,0,0);
// if you want custom version for app
cordovaProject.config().setVersion(Version.create()
    .version("1.0.0")
    .iosCfBundleVersion("3.3.3")
    .androidVersionCode(7));

cordovaProject.config().setDescription("description");
// author
cordovaProject.config().author().setEmail("adad");
cordovaProject.config().author().setHref("adad");
cordovaProject.config().author().setName("name");
// access
cordovaProject.config().access().add("*");
// with launch-external
cordovaProject.config().access().add("*", "yes");
cordovaProject.config().access().add(Access.create().origin("*").value("disable").subdomains(true));
cordovaProject.config().preferences().add("name", "value");
// allow navigation
cordovaProject.config().allowNavigation().add("*");
cordovaProject.config().allowNavigation().add("http://*");
// icon
cordovaProject.config().icon().add("src/img/icon.png");
cordovaProject.config().icon().add(Icon.create().src("src/img").height(100).width(100).density("zsdad"));
// platform icon
cordovaProject.config().platform(Platform.Android).icon().add("src/img/android/icon.png")
cordovaProject.config().platform(Platform.Android).icon().add(Icon.create().src("src/img").density("ldpi"));
// platform splash
cordovaProject.config().platform(Platform.Android).splash().add(Splash.create().src("dest/splash.png").density("low"));
cordovaProject.config().platform(Platform.IOs).splash().add(Splash.create().src("dest/splash.png").width(320).height(100));
// platform preferences
cordovaProject.config().platform(Platform.Android).preferences().add("name", "value");

//or for better perfomance, do a transaction
cordovaProject.config(new ConfigTransactionJob() {
    @Override
    public void exec(CordovaConfig config) throws IOException {
        config.setName("hello");
        config.setVersion(Version.create().version("100"));
        config.setDescription("description");
    }
}
```

Read data for the current project

```java
String name = cordovaProject.config().getName();
String version = cordovaProject.config().getVersion();
String description = cordovaProject.config().getDescription();

String email = cordovaProject.config().author().getEmail();
String href = cordovaProject.config().author().getHref();
String name = cordovaProject.config().author().getName();

List<Icon> icons = cordovaProject.config().icon().getAll();
List<Access> access = cordovaProject.config().access().getAll();
List<AllowNavigation> = cordovaProject.config().allowNavigation().getAll();
List<Preferences> preferences = cordovaProject.config().preferences().getAll();
List<Icon> icons = cordovaProject.config().platform(Platform.Android).icon().getAll();
List<Splash> splash = cordovaProject.config().platform(Platform.IOs).splash().getAll();


// I want to add a new icon in the recently changed config.xml

File file = cordovaProject.getProject();
Path dest = file.toPath().resolve(icon.get(0).getSrc());
Files.copy(src, dest);

// or if you are already changing your config.xml
Icon icon = Icon.create().src("src/img").height(100).width(100);
cordovaProject.config().icon().add(icon);
Files.copy(src, cordovaProject.getProject().toPath().resolve(icon.getSrc()));
```

## LICENSE:

Cordova Java is released under the MIT License.

