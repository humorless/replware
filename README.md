# How to test

```
boot dev
```

# How to deploy

```
boot build-prod
```

## Trouble shooting

I got this exception since last time I used boot:
```
                                    boot.watcher/make-watcher                        watcher.clj:  124
                                    boot.watcher/make-watcher                        watcher.clj:  127
                                         boot.watcher/service                        watcher.clj:   94
                               boot.watcher/new-watch-service                        watcher.clj:   72
com.barbarysoftware.watchservice.WatchService.newWatchService                  WatchService.java:   69
java.lang.UnsupportedOperationException: barbarywatchservice not supported on Mac OS X prior to Leopard (10.5)
             clojure.lang.ExceptionInfo: barbarywatchservice not supported on Mac OS X prior to Leopard (10.5)
```

### The workaround to get a runnable boot

1. Clone git@github.com:boot-clj/boot.git
2. Change version number to 2.8.5
3. Build the boot from source
```
make deps
make install
```

### The way to run boot

For some reasons I did not understand, when I gave command line command, I always accessed the old version of boot.
```
java -jar $HOME/.boot/cache/bin/2.8.5/boot.jar dev
```

