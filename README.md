# OpenLync

**OpenLync** is an instant communication software developed in Java with focus in the corporate environment. It aims to become a competitor to the genre applications such as [Microsoft Lync](http://apps.microsoft.com/windows/pt-br/app/lync/ba4b9485-8712-41ff-a9ea-6243a3e07682), [Spark](http://www.igniterealtime.org/projects/spark/) and [Openfire Server](http://www.igniterealtime.org/projects/openfire/).*

***OpenLync** é um aplicativo de comunicação instantânea desenvolvido em Java com foco no ambiente corporativo. Tem por objetivo tornar-se um concorrente aos aplicativos do gênero, como [Microsoft Lync](http://apps.microsoft.com/windows/pt-br/app/lync/ba4b9485-8712-41ff-a9ea-6243a3e07682), [Spark](http://www.igniterealtime.org/projects/spark/) e [Openfire Server](http://www.igniterealtime.org/projects/openfire/).*

*Software apresentado como trabalho de conclusão semestral para o curso Técnico em Informática com Habilitação em Desenvolvimento de Software, CEDUP Timbó - Centro de Educação Profissional. Timbó, SC. Brasil.*

### Main features

* See online and offline users
* Manage friends
* Profile pictures
* New message desktop notification
* Offline messages
* Conversation history
* User management with graphical user interface
* Server status
* Users online counter

### Client
![client1](https://github.com/RobertoDebarba/OpenLync/blob/master/screenshots/client1.png)
![client2](https://github.com/RobertoDebarba/OpenLync/blob/master/screenshots/client2.png)
![client3](https://github.com/RobertoDebarba/OpenLync/blob/master/screenshots/client3.png)
![client4](https://github.com/RobertoDebarba/OpenLync/blob/master/screenshots/client4.png)

### Manager
![manager1](https://github.com/RobertoDebarba/OpenLync/blob/master/screenshots/manager1.png)
![manager2](https://github.com/RobertoDebarba/OpenLync/blob/master/screenshots/manager2.png)
![manager3](https://github.com/RobertoDebarba/OpenLync/blob/master/screenshots/manager3.png)

## Prerequisites

- JDK 8
- [Maven](https://maven.apache.org/)
- [MySQL](https://www.mysql.com/)

## How to run

1. Create MySQL database (doc/base.sql)
1. Run project open-lync-server (It will start on socket port 0)
1. Edit configuration file .OpenLync_service.cfg in home folder with server port (default 0)
1. Restart open-lync-server
1. Run open-lync-manager
1. Click in the engine icon on login screen
1. Configure database access info
1. Save and login on manager. Now you can manage your user accounts
1. Run open-lync-client
1. Click in the engine icon on login screen
1. Save and login
1. All done! Enjoy.

## Credits

* [Roberto Luiz Debarba](https://github.com/RobertoDebarba)
* Special thanks:
  * [Jonathan Suptitz](https://github.com/jonnymohamed)
  * [Luan Purim](https://github.com/Feenux)
  * [CEDUP Timbó](https://www.facebook.com/ceduptimbo/)

## Aditional info

This project was created in 2014, and was my first programming project in Java, so be aware that this is the worst code you've ever seen. Be warned =)

*Este projeto foi criado em 2014, e foi meu primeiro projeto de programação em Java, então tenha ciencia que esse é o pior código que você já viu. Esteja avisado =)*

## Licença

GNU General Public License v3.0
