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
CordovaProject cordovaPrj = cli.create(path, "dirName", "com.upplication.sample", "Sample");
// you can get the CordovaProject object from an existing cordova project
CordovaProject cordovaPrj = Cordova.getProject(new File("/home/documents/existing-project"));
```

```java
// add
cordovaPrj.platform().add(Platform.IOs); // TODO: maybe addIos();
cordovaPrj.platform().add(Platform.Android);
// remove
cordovaPrj.platform().remove(Platform.Android); // TODO: maybe removeAndroid();
// list
List<Platform> platforms = cordovaPrj.platform().list();
```

```java
cordovaPrj.build();
cordovaPrj.build(Platform.IOs);
cordovaPrj.prepare();
cordovaPrj.prepare(Platform.IOs);
cordovaPrj.compile();
cordovaPrj.compile(Platform.IOs);
```


