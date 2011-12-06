(defproject normalize-headers "1.0.0-SNAPSHOT"
  :description "FIXME: write description"
  :dependencies [[org.clojure/clojure "1.3.0"]
                 [lambda "1.0.1-SNAPSHOT"]
                 [cascalog "1.8.4"]
                 [org.clojure/tools.cli "0.2.1"]
                 [factual/cascalog-commons "1.1.0-SNAPSHOT"]
                 [clojure-csv/clojure-csv "1.3.2"]
                 [cadr "1.0.1-SNAPSHOT"]]
  :dev-dependencies [[org.apache.hadoop/hadoop-core "0.20.2-dev"]]
  :repositories {"releases" {:url "http://maven.corp.factual.com/nexus/content/repositories/releases"}
                 "snapshots" {:url "http://maven.corp.factual.com/nexus/content/repositories/snapshots"}
                 "public" {:url "http://maven.corp.factual.com/nexus/content/groups/public/"}}
  :main normalize-headers.core)
