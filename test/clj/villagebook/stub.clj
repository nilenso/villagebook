(ns villagebook.stub)

(defn create-user-map
  [email password nickname name]
  {:email email :password password :nickname nickname :name name})

(def user1 (apply create-user-map ["geek@gorilla.com" "testpassword" "ronaldo" "Zeus"]))
(def user2 (apply create-user-map ["oneplus@two.com" "testpassword" "nick" nil]))
