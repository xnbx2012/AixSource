package cn.peterzhongColor.aix;

import com.google.appinventor.components.annotations.DesignerComponent;
import com.google.appinventor.components.annotations.SimpleFunction;
import com.google.appinventor.components.annotations.SimpleObject;
import com.google.appinventor.components.common.ComponentCategory;
import com.google.appinventor.components.runtime.AndroidNonvisibleComponent;
import com.google.appinventor.components.runtime.Component;
import com.google.appinventor.components.runtime.ComponentContainer;

@DesignerComponent(version=1, category=ComponentCategory.EXTENSION, nonVisible=true, description="To convert color models,including color models:<ul><li>RGB:3 value,without alpha value(default:255)</li><li>\r\nHexValue:6 digits</li><li>AppInventor Value:App Inventor color,it's negative</li></ul>Click `more information`to know more", helpUrl="https://peterzhong1219.gitee.io/colorExtension.html", iconName="http://thyrsi.com/t6/386/1539421222x1822611263.jpg")
@SimpleObject(external=true)
public class Color
  extends AndroidNonvisibleComponent
  implements Component
{
  public Color(ComponentContainer container)
  {
    super(container.$form());
  }
  
  @SimpleFunction(description="Input RGB value,convert to 16 hexadecimal string(Without `#` inculded.")
  public String RGB2HEX(int r, int g, int b)
  {
    int red = r / 16;
    int rred = r % 16;
    String rFString;
    if (red == 10)
    {
      rFString = "A";
    }
    else
    {
      String rFString;
      if (red == 11)
      {
        rFString = "B";
      }
      else
      {
        String rFString;
        if (red == 12)
        {
          rFString = "C";
        }
        else
        {
          String rFString;
          if (red == 13)
          {
            rFString = "D";
          }
          else
          {
            String rFString;
            if (red == 14)
            {
              rFString = "E";
            }
            else
            {
              String rFString;
              if (red == 15) {
                rFString = "F";
              } else {
                rFString = String.valueOf(red);
              }
            }
          }
        }
      }
    }
    String rSString;
    String rSString;
    if (rred == 10)
    {
      rSString = "A";
    }
    else
    {
      String rSString;
      if (rred == 11)
      {
        rSString = "B";
      }
      else
      {
        String rSString;
        if (rred == 12)
        {
          rSString = "C";
        }
        else
        {
          String rSString;
          if (rred == 13)
          {
            rSString = "D";
          }
          else
          {
            String rSString;
            if (rred == 14)
            {
              rSString = "E";
            }
            else
            {
              String rSString;
              if (rred == 15) {
                rSString = "F";
              } else {
                rSString = String.valueOf(rred);
              }
            }
          }
        }
      }
    }
    String rFString = rFString + rSString;
    
    int green = g / 16;
    int rgreen = g % 16;
    String gFString;
    if (green == 10)
    {
      gFString = "A";
    }
    else
    {
      String gFString;
      if (green == 11)
      {
        gFString = "B";
      }
      else
      {
        String gFString;
        if (green == 12)
        {
          gFString = "C";
        }
        else
        {
          String gFString;
          if (green == 13)
          {
            gFString = "D";
          }
          else
          {
            String gFString;
            if (green == 14)
            {
              gFString = "E";
            }
            else
            {
              String gFString;
              if (green == 15) {
                gFString = "F";
              } else {
                gFString = String.valueOf(green);
              }
            }
          }
        }
      }
    }
    String gSString;
    String gSString;
    if (rgreen == 10)
    {
      gSString = "A";
    }
    else
    {
      String gSString;
      if (rgreen == 11)
      {
        gSString = "B";
      }
      else
      {
        String gSString;
        if (rgreen == 12)
        {
          gSString = "C";
        }
        else
        {
          String gSString;
          if (rgreen == 13)
          {
            gSString = "D";
          }
          else
          {
            String gSString;
            if (rgreen == 14)
            {
              gSString = "E";
            }
            else
            {
              String gSString;
              if (rgreen == 15) {
                gSString = "F";
              } else {
                gSString = String.valueOf(rgreen);
              }
            }
          }
        }
      }
    }
    String gFString = gFString + gSString;
    
    int blue = b / 16;
    int rblue = b % 16;
    String bFString;
    if (blue == 10)
    {
      bFString = "A";
    }
    else
    {
      String bFString;
      if (blue == 11)
      {
        bFString = "B";
      }
      else
      {
        String bFString;
        if (blue == 12)
        {
          bFString = "C";
        }
        else
        {
          String bFString;
          if (blue == 13)
          {
            bFString = "D";
          }
          else
          {
            String bFString;
            if (blue == 14)
            {
              bFString = "E";
            }
            else
            {
              String bFString;
              if (blue == 15) {
                bFString = "F";
              } else {
                bFString = String.valueOf(blue);
              }
            }
          }
        }
      }
    }
    String bSString;
    String bSString;
    if (rblue == 10)
    {
      bSString = "A";
    }
    else
    {
      String bSString;
      if (rblue == 11)
      {
        bSString = "B";
      }
      else
      {
        String bSString;
        if (rblue == 12)
        {
          bSString = "C";
        }
        else
        {
          String bSString;
          if (rblue == 13)
          {
            bSString = "D";
          }
          else
          {
            String bSString;
            if (rblue == 14)
            {
              bSString = "E";
            }
            else
            {
              String bSString;
              if (rblue == 15) {
                bSString = "F";
              } else {
                bSString = String.valueOf(rblue);
              }
            }
          }
        }
      }
    }
    String bFString = bFString + bSString;
    String result = "" + rFString + gFString + bFString;
    return result;
  }
  
  @SimpleFunction(description="Input HEX string,return AI color value")
  public long HEX2AI(String hexValue)
  {
    long decimal = Integer.parseInt(hexValue, 16);
    long plus = 4278190080L;
    long last = decimal + 4278190080L;
    long result = last - 4294967295L;
    return result;
  }
  
  @SimpleFunction(description="Input AI color value,return HEX string")
  public String AI2HEX(int aiValue)
  {
    long plus = aiValue + 4294967295L;
    long last = plus - 4278190080L;
    int last2 = (int)last;
    String result = Integer.toHexString(last2);
    return result;
  }
}
