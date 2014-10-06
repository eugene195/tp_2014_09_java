Технопарк@Mail.ru
 ============
 Курс: Углубленное программирование на Java

 Учебный период: осенний семестр 2014г.

 ##Студенты
 Григорьев Евгений, eugene195, eugene.07@list.ru
 Кисленко Максим, xammi, kmg.xammi.1@gmail.com
 Моисеев Максим, streambuf streambuf@mail.ru


 Подробное о проекте можно прочесть на сайте [Технопарка] [1]
 
 [1]: https://tech-mail.ru/

 ########################
Подключение по SSH
########################

// подключаемся к удаленному серверу
ssh g01@tp-demo1.tech-mail.ru

// вводим пароль:
***

// проект уже склонирован
cd tp_2014_09_java/

// запускаем сервер
java -cp 2014_Java_Technopark.jar global.Main 9081

// чтобы обратиться к серверу в браузере
g01.javaprojects.tp-dev.ru/

// команда для копирования jar-файла с готовым сервером
scp /home/max/2014_Java_Technopark/out/artifacts/2014_Java_Technopark_jar/2014_Java_Technopark.jar g01@tp-demo1.tech-mail.ru:/home/g01/tp_2014_09_java
