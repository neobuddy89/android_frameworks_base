/**
 * 
 */
package android.content.pm;

import java.util.HashMap;
import java.util.Map;

import org.xmlpull.v1.XmlPullParserException;

import android.os.Parcel;
import android.os.Parcelable;
import android.util.AttributeSet;

/**
 * Overall information about "theme" package.  This corresponds
 * to the information collected from AndroidManifest.xml (theme tag).
 *
 * Below is an example of theme tag
 *    <theme
 *        pluto:name="Pluto Default"
 *        pluto:thumbnail="@drawable/app_thumbnail"
 *        pluto:author="John Doe"
 *        pluto:ringtoneFileName="media/audio/ringtone.mp3"
 *        pluto:notificationRingtoneFileName="media/audio/locked/notification.mp3"
 *        pluto:copyright="T-Mobile, 2009"
 *        pluto:wallpaperImage="media/images/wallpaper.jpg"
 *        pluto:favesBackground="media/images/locked/background.jpg"
 *        pluto:soundpackName="<package_name>/<sound_pack_name>"
 *    />
 *
 */
public final class ThemeInfo extends BaseThemeInfo {

    /**
     * The theme resource id of parent theme
     *
     * @see parentThemeId attribute
     *
     */
    public int parentThemeId = -1;

    /**
     * The package name of parent theme
     *
     * @see parentThemePackageName attribute
     *
     */
    public String parentThemePackageName = "";

    /**
     * {@hide}
     */
    public boolean hasColorPalette = false;

    /**
     * {@link #themePackage}
     *
     */
    private static final int THEME_PACKAGE_INDEX = 0;

    /**
     * {@link #thumbnail}
     *
     */
    private static final int THUMBNAIL_INDEX = 1;

    /**
     * {@link #author}
     *
     */
    private static final int AUTHOR_INDEX = 2;

    /**
     * {@link #ringtoneName}
     *
     */
    private static final int RINGTONE_FILE_NAME_INDEX = 3;

    /**
     * {@link #notificationRingtoneName}
     *
     */
    private static final int NOTIFICATION_RINGTONE_FILE_NAME_INDEX = 4;

    /**
     * {@link #favesImageName}
     *
     */
    private static final int FAVES_IMAGE_NAME_INDEX = 5;

    /**
     * {@link #favesAppImageName}
     *
     */
    private static final int FAVES_APP_IMAGE_NAME_INDEX = 6;

    /**
     * {@link #wallpaperImageName}
     *
     */
    private static final int WALLPAPER_IMAGE_NAME_INDEX = 7;

    /**
     * {@link #copyright}
     *
     */
    private static final int COPYRIGHT_INDEX = 8;

    /**
     * {@link #theme}
     *
     */
    private static final int THEME_INDEX = 9;

    /**
     * {@link #ringtoneName}
     *
     */
    private static final int RINGTONE_NAME_INDEX = 10;

    /**
     * {@link #notificationRingtoneName}
     *
     */
    private static final int NOTIFICATION_RINGTONE_NAME_INDEX = 11;

    /**
     * {@link #soundPackName}
     *
     */
    private static final int SOUNDPACK_NAME_INDEX = 12;

    /**
     * {@link #parentThemeId}
     *
     */
    private static final int PARENT_THEME_INDEX = 13;

    /**
     * {@link #parentThemePackageName}
     *
     */
    private static final int PARENT_THEME_PACKAGE_INDEX = 14;

    /**
     * {@link #parentThemePackageName}
     *
     */
    private static final int THEME_HAS_COLOR_PALETTE_INDEX = 15;


    private static final String [] compulsoryAttributes = new String [] {
        "name",
        "thumbnail",
        "author",
    };

    private static final String [] optionalAttributes = new String [] {
        "ringtoneFileName",
        "notificationRingtoneFileName",
        "favesBackground",
        "favesAppsBackground",
        "wallpaperImage",
        "copyright",
        "androidUiStyle",
        "ringtoneName",
        "notificationRingtoneName",
        "soundpackName",
        "parentThemeId",
        "parentThemePackageName",
        "hasColorPalette",
    };

    private static Map<String, Integer> attributesLookupTable;

    static {
        attributesLookupTable = new HashMap<String, Integer>();
        for (int i = 0; i < compulsoryAttributes.length; i++) {
            attributesLookupTable.put(compulsoryAttributes[i], i);
        }

        for (int i = 0; i < optionalAttributes.length; i++) {
            attributesLookupTable.put(optionalAttributes[i], compulsoryAttributes.length + i);
        }
    }

    public ThemeInfo(AttributeSet attrs) throws XmlPullParserException {
        super();

        type = InfoObjectType.TYPE_THEME;
        Map<String, Integer> tempMap = new HashMap<String, Integer>(attributesLookupTable);
        int numberOfCompulsoryAttributes = 0;
        for (int i = 0; i < attrs.getAttributeCount(); i++) {
            String key = attrs.getAttributeName(i);
            if (tempMap.containsKey(key)) {
                int index = tempMap.get(key);
                tempMap.remove(key);

                if (index < compulsoryAttributes.length) {
                    numberOfCompulsoryAttributes++;
                }
                switch (index) {
                    case THEME_PACKAGE_INDEX:
                        // theme name
                        name = attrs.getAttributeValue(i);
                        break;

                    case THUMBNAIL_INDEX:
                        // theme thumbprint
                        thumbnail = attrs.getAttributeResourceValue(i, -1);
                        break;

                    case AUTHOR_INDEX:
                        // theme author
                        author = attrs.getAttributeValue(i);
                        break;

                    case RINGTONE_FILE_NAME_INDEX:
                        // ringtone
                        ringtoneFileName = attrs.getAttributeValue(i);
                        changeDrmFlagIfNeeded(ringtoneFileName);
                        break;

                    case NOTIFICATION_RINGTONE_FILE_NAME_INDEX:
                        // notification ringtone
                        notificationRingtoneFileName = attrs.getAttributeValue(i);
                        changeDrmFlagIfNeeded(notificationRingtoneFileName);
                        break;

                    case FAVES_IMAGE_NAME_INDEX:
                        // faves background
                        favesImageName = attrs.getAttributeValue(i);
                        changeDrmFlagIfNeeded(favesImageName);
                        break;

                    case FAVES_APP_IMAGE_NAME_INDEX:
                        // favesAppBackground attribute
                        favesAppImageName = attrs.getAttributeValue(i);
                        changeDrmFlagIfNeeded(favesAppImageName);
                        break;

                    case WALLPAPER_IMAGE_NAME_INDEX:
                        // wallpaperImage attribute
                        wallpaperImageName = attrs.getAttributeValue(i);
                        changeDrmFlagIfNeeded(wallpaperImageName);
                        break;

                    case COPYRIGHT_INDEX:
                        // themeCopyright attribute
                        copyright = attrs.getAttributeValue(i);
                        break;

                    case THEME_INDEX:
                        // androidUiStyle attribute
                        theme = attrs.getAttributeResourceValue(i, -1);
                        break;

                    case RINGTONE_NAME_INDEX:
                        // ringtone UI name
                        ringtoneName = attrs.getAttributeValue(i);
                        break;

                    case NOTIFICATION_RINGTONE_NAME_INDEX:
                        // notification ringtone UI name
                        notificationRingtoneName = attrs.getAttributeValue(i);
                        break;

                    case SOUNDPACK_NAME_INDEX:
                        soundPackName = attrs.getAttributeValue(i);
                        break;

                    case PARENT_THEME_INDEX:
                        parentThemeId = attrs.getAttributeIntValue(i, -1);
                        break;  

                    case PARENT_THEME_PACKAGE_INDEX:
                        parentThemePackageName = attrs.getAttributeValue(i);
                        break;  

                    case THEME_HAS_COLOR_PALETTE_INDEX:
                        hasColorPalette = attrs.getAttributeValue(i).equalsIgnoreCase("true");
                        break;
                }
            }
        }
        if (numberOfCompulsoryAttributes < compulsoryAttributes.length) {
            throw new XmlPullParserException("Not all compulsory attributes are specified in <theme>");
        }
    }

    /*
     * Flatten this object in to a Parcel.
     * 
     * @param dest The Parcel in which the object should be written.
     * @param flags Additional flags about how the object should be written.
     * May be 0 or {@link #PARCELABLE_WRITE_RETURN_VALUE}.
     * 
     * @see android.os.Parcelable#writeToParcel(android.os.Parcel, int)
     */
    public void writeToParcel(Parcel dest, int flags) {
        super.writeToParcel(dest, flags);
        dest.writeInt(parentThemeId);
        dest.writeString(parentThemePackageName);
        dest.writeInt(hasColorPalette? 1 : 0);
    }

    public static final Parcelable.Creator<ThemeInfo> CREATOR
            = new Parcelable.Creator<ThemeInfo>() {
        public ThemeInfo createFromParcel(Parcel source) {
            return new ThemeInfo(source);
        }

        public ThemeInfo[] newArray(int size) {
            return new ThemeInfo[size];
        }
    };

    private ThemeInfo(Parcel source) {
        super(source);

        parentThemeId = source.readInt();
        parentThemePackageName = source.readString();
        hasColorPalette = source.readInt() != 0;
    }

}
