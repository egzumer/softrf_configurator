# Repackages optimized classes into %packageName%.repacked package
# in resulting AIX. Repackaging is necessary to avoid clashes with
# the other extensions that might be using same libraries as you.
-repackageclasses %packageName%.repacked

-android
-optimizationpasses 4
-allowaccessmodification
-mergeinterfacesaggressively
-overloadaggressively
-useuniqueclassmembernames
-dontskipnonpubliclibraryclasses
-dontskipnonpubliclibraryclassmember
