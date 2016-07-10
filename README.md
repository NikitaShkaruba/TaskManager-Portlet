# About
TaskManger is a liferay portlet - tiny application embedded in some of Liferay pages.
Task Manager is a Task manager, haha, which means you can assign(create) tasks for other employees,
view tasks assigned for you, browse tasks, and edit them. It is an alpha version, so there may be bugs.

# Capabilities
* View all opened tasks for your liferay user
* Get filtered list of tasks
* Get full information about concrete task, attach some files to it, write comments
* Create new tasks for whatever user
* Edit task - close it, assign new executor, etc..

# Installation
1. Install liferay portal on your server
2. Compile Portlet bu running "mvn package" from project root
3. Move resulting TaskManager.war archive to ${LIFERAY_HOME}/autodeploy
4. Wait while liferay does unpacking and installing. You can check logs for insurance in successful deployment
5. Open up liferay site in browser, find "Welcome {user} admin" -> add application -> TaskManager. Place it somewhere.
6. Have fun using it