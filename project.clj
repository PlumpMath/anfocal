(defproject anfocal/anfocal "0.1.0-SNAPSHOT"
  :description "FIXME: Android project description"
  :url "http://example.com/FIXME"
  :license {:name "Eclipse Public License"
            :url "http://www.eclipse.org/legal/epl-v10.html"}

  :global-vars {*warn-on-reflection* true}

  :source-paths ["src/clojure" "src"]
  :java-source-paths ["src/java"]
  :javac-options ["-target" "1.6" "-source" "1.6" "-Xlint:-options"]
  :plugins [[lein-droid "0.4.3"]]

  :dependencies [[org.clojure-android/clojure "1.7.0-r2"]
                 [neko/neko "4.0.0-alpha5"]]
  :profiles {:default [:dev]

             :dev
             [:android-common :android-user
              {:dependencies [[org.clojure/tools.nrepl "0.2.10"]
                              [cider/cider-nrepl "0.9.1"]]
               :target-path "target/debug"
               :android {:aot :all-with-unused
                         :rename-manifest-package "com.lambdacat.anfocal.debug"
                         :manifest-options {:app-name "AnFocal (debug)"}}}]
             :release
             [:android-common
              {:target-path "target/release"
               :android
               {;; :keystore-path "/home/user/.android/private.keystore"
                ;; :key-alias "mykeyalias"
                ;; :sigalg "MD5withRSA"

                :ignore-log-priority [:debug :verbose]
                :aot :all
                :build-type :release}}]

                :lean
                [:release
                 {:dependencies ^:replace [[org.skummet/clojure "1.7.0-RC3-SNAPSHOT" :use-resources true]
                                           [neko/neko "4.0.0-alpha5"]]
                  :exclusions [[org.clojure/clojure]
                               [org.clojure-android/clojure]]
                  :jvm-opts ["-Dclojure.compile.ignore-lean-classes=true"]
                  :global-vars ^:replace {clojure.core/*warn-on-reflection* true}
                  :android {:lean-compile true
                            :skummet-skip-vars ["#'neko.-utils/keyword->static-field"
                                                "#'neko.-utils/keyword->setter"
                                                "#'neko.ui.traits/get-display-metrics"
                                                "#'com.lambdacat.AnFocal.main/AnFocalActivity-onCreate"
                                                "#'com.lambdacat.AnFocal.main/AnFocalActivity-init"]}}]
                              }

  :android {;; Specify the path to the Android SDK directory.
             :sdk-path "/usr/local/opt/android-sdk"

            ;; Try increasing this value if dexer fails with
            ;; OutOfMemoryException. Set the value according to your
            ;; available RAM.
            :dex-opts ["-JXmx4096M" "--incremental"]

            :target-version "23"
            :aot-exclude-ns ["clojure.parallel" "clojure.core.reducers"
                             "cider.nrepl" "cider-nrepl.plugin"
                             "cider.nrepl.middleware.util.java.parser"
                             #"cljs-tooling\..+"]}

                             )
