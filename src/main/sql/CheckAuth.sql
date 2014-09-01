SELECT * FROM User
WHERE `login`='$login'
  AND `passw`=md5('$passw');