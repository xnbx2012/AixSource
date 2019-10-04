package com.peterzhong.shortcut;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import com.google.appinventor.components.annotations.DesignerComponent;
import com.google.appinventor.components.annotations.SimpleFunction;
import com.google.appinventor.components.annotations.SimpleObject;
import com.google.appinventor.components.annotations.UsesPermissions;
import com.google.appinventor.components.common.ComponentCategory;
import com.google.appinventor.components.runtime.AndroidNonvisibleComponent;
import com.google.appinventor.components.runtime.Component;
import com.google.appinventor.components.runtime.ComponentContainer;
import java.io.FileInputStream;

@DesignerComponent(description="To create a shorcut on the desktop of an Android phone below O.Need the permission of creating a shortcut", iconName="images/extension.png", nonVisible=true, version=1, category=ComponentCategory.EXTENSION, showOnPalette=true)
@SimpleObject(external=true)
@UsesPermissions(permissionNames="com.android.launcher.permission.INSTALL_SHORTCUT,com.android.launcher2.permission.INSTALL_SHORTCUT,com.android.launcher3.permission.INSTALL_SHORTCUT")
public class Shorcut
  extends AndroidNonvisibleComponent
  implements Component
{
  Context context;
  FileInputStream in;
  
  public Shorcut(ComponentContainer container)
  {
    super(container.$form());
    this.context = container.$context();
  }
  
  @SimpleFunction(description="To create a shorcut on the desktop of an Android phone below O")
  public void CreateShortcut(String name, String icon, String packageName, boolean duplicate)
  {
    Intent shortcutIntent = new Intent("com.android.launcher.action.INSTALL_SHORTCUT");
    Bitmap iconObj = BitmapFactory.decodeFile(icon);
    shortcutIntent.putExtra("android.intent.extra.shortcut.NAME", name);
    shortcutIntent.putExtra("android.intent.extra.shortcut.ICON", iconObj);
    shortcutIntent.putExtra("duplicate", duplicate);
    Intent launchIntent = new Intent();
    launchIntent.setClassName(this.context, packageName);
    shortcutIntent.putExtra("android.intent.extra.shortcut.INTENT", launchIntent);
    this.context.sendBroadcast(shortcutIntent);
  }
}
