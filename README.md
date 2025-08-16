# Setup Guide: Java TodoApp on Ubuntu

This guide walks you through building, packaging, installing, configuring autostart (with a 10-second delay), and managing backups for the Java TodoApp.  
**Note:** The app stores tasks at `~/.todo_tasks.txt`. Always back up this file before uninstalling or updating.

---

## 1. Compile and Package into a JAR

```bash
javac TodoApp.java
jar --create --file TodoApp.jar --main-class=TodoApp TodoApp.class
```

---

## 2. Build a .deb Package using jpackage

```bash
jpackage --name TodoApp --input . --main-jar TodoApp.jar --main-class TodoApp \
  --type deb --icon myapp.png
```

This will generate a file like `TodoApp-1.0.deb`.

---

## 3. Install the .deb Package

```bash
sudo apt install ./TodoApp*.deb
```

---

## 4. Verify Installation

Check for the desktop entry:

```bash
ls /usr/share/applications | grep -i todo
```

Check the executable path:

```bash
cat /usr/share/applications/todoapp-*.desktop
```

Run directly (example path may differ):

```bash
/opt/todoapp/bin/TodoApp
```

---

## 5. Create Autostart Entry (with 10s Delay)

```bash
mkdir -p ~/.config/autostart
nano ~/.config/autostart/todo.desktop
```

Paste the following:

```ini
[Desktop Entry]
Type=Application
Name=TodoApp
Exec=bash -c "sleep 10 && /opt/todoapp/bin/TodoApp"
X-GNOME-Autostart-enabled=true
```

Save and exit.

---

## 6. (Optional) Run App from Terminal with TodoApp

Create a symlink:

```bash
sudo ln -s /opt/todoapp/bin/TodoApp /usr/local/bin/TodoApp
```

Now you can launch with:

```bash
TodoApp
```

---

## 7. Test Autostart

Reboot:

```bash
sudo reboot
```

TodoApp will launch automatically ~10s after login.

---

## 8. Backup & Restore Task Files

The app stores tasks at:

```
~/.todo_tasks.txt
```

**Backup:**

```bash
cp ~/.todo_tasks.txt ~/todo_tasks_backup.txt
```

**Restore:**

```bash
cp ~/todo_tasks_backup.txt ~/.todo_tasks.txt
```

---

## 9. Uninstall TodoApp

Find the package name:

```bash
dpkg -l | grep todo
```

Remove it:

```bash
sudo apt remove todoapp
```

Remove symlink (if created):

```bash
sudo rm /usr/local/bin/TodoApp
```

---

## 10. Update TodoApp

Rebuild the .deb with a new version:

```bash
jpackage --name TodoApp --input . --main-jar TodoApp.jar --main-class TodoApp \
  --type deb --icon myapp.png --app-version 1.1
```

Install the new version (it replaces the old one):

```bash
sudo apt install ./TodoApp-1.1.deb
```

Verify update:

```bash
dpkg -l | grep todo
```

Restore tasks if needed (see Section 8).

---

**Tip:** Always back up `~/.todo_tasks.txt` before uninstalling or updating TodoApp, and restore it after reinstallation or update.