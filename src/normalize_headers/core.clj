(ns normalize-headers.core
  (:use lambda.core
        cascalog.api
        cascalog-commons.core
        clojure.tools.cli
        clojure-csv.core
        cadr.core
        clojure.string))

(def split-line
  (λ ([line]
        (split-line line *delimiter*))
     ([line delimiter]
        (binding [*delimiter* delimiter]
          (car (parse-csv line))))))

(def normalize
  (λ [in-headers out-headers line delimiter]
     (let [header->value (zipmap in-headers (split-line line delimiter))]
       (join delimiter (map #(get header->value % "") out-headers)))))

(def -main
  (λ [& args]
     (let [[{sink :sink
             in-headers :in-headers
             out-headers :out-headers
             comma-delimited :comma-delimited
             tab-delimited :tab-delimited}
            files
            usage]
           (cli args
                ["-s" "--sink" "Output to sink (defaults to stdout)"
                 :name :sink
                 :default false]
                ["-i" "--in-headers" "Input-file headers"
                 :name :in-headers]
                ["-o" "--out-headers" "Output-file headers"
                 :name :out-headers]
                ["-c" "--comma-delimited" "Comma-delimited files"
                 :name :comma-delimited
                 :default true
                 :flag true]
                ;; How to set up mutually exclusive flags?
                ["-t" "--tab-delimited" "Tab-delimited files"
                 :name :tab-delimited
                 :default false
                 :flag true])]
       (if (empty? files)
         (println usage)
         (let [delimiter
               (if (and comma-delimited (not tab-delimited)) \,\tab)
               in-file (car files)
               sink (if sink
                      (hfs-textline sink)
                      (stdout))]
           (let [in-headers (split-line in-headers \,)
                 out-headers (split-line out-headers \,)]
             (?<- sink [!normalized-line]
                  ((hfs-textline in-file) !line)
                  (normalize in-headers out-headers !line delimiter :> !normalized-line)
                  (:distinct false))))))))