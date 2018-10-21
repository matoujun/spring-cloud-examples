package org.matoujun.cloud.common;

import org.junit.Test;
import org.matoujun.cloud.common.ObjectSerializeUtil;
import org.matoujun.cloud.common.User;

import java.io.IOException;
import java.util.ArrayList;

/**
 * @author matoujun

 */
public class PlainTest {

    @Test
    public void userSerial(){
        User user = new User();
        user.setUsername("name");
        user.setMobile("133333333");
        String str = "";
        try {
             str = ObjectSerializeUtil.toSerializableString(user);
        } catch (IOException e) {
            e.printStackTrace();
        }
        System.out.println("userSerial: " + str);
    }

    @Test
    public void strToUser(){
        try {
            User user = ObjectSerializeUtil.fromSerializableString("rO0ABXNyABxjb20uZGlkaS5iaWFucXVlLmNvbW1vbi5Vc2Vy2Bk0N2qZRCoCACFCAAhyb2xlRmxhZ0wAC2FjY2Vzc1Rva2VudAASTGphdmEvbGFuZy9TdHJpbmc7TAAKYml6TGluZVNldHQAD0xqYXZhL3V0aWwvU2V0O0wADWJ1c2luZXNzTGluZXN0ABBMamF2YS91dGlsL0xpc3Q7TAAHYnV0dG9uc3EAfgADTAAGY2l0aWVzcQB+AANMAAdjaXR5U2V0cQB+AAJMAAJjbnEAfgABTAAHY291bnRyeXEAfgADTAAKY291bnRyeVNldHEAfgACTAAKZGVwYXJ0bWVudHEAfgABTAALZGlzcGxheU5hbWVxAH4AAUwAC2VtYWlsUHJlZml4cQB+AAFMAAlleHBpcmVzSW50ABNMamF2YS9sYW5nL0ludGVnZXI7TAAHaXNBZG1pbnQAE0xqYXZhL2xhbmcvQm9vbGVhbjtMAARtYWlscQB+AAFMAAVtZW51c3EAfgADTAAGbW9iaWxlcQB+AAFMAApwZXJtaXNzaW9ucQB+AAFMAAtwcm92aW5jZVNldHEAfgACTAAMcHJvdmluY2VTZXRzcQB+AANMAAlwcm92aW5jZXNxAH4AA0wAC3JlZGlyZWN0VXJpcQB+AAFMABFyZWdpb25CaXpMaW5lQXV0aHEAfgAFTAAJcmVnaW9uU2V0cQB+AAJMAAdyZWdpb25zcQB+AANMAAtyb2xlTWFya1NldHEAfgACTAAFcm9sZXNxAH4AA0wACXN1YkNpdGllc3EAfgADTAAKc3ViY2l0eVNldHEAfgACTAAGdGlja2V0cQB+AAFMAAZ1c2VySWR0ABBMamF2YS9sYW5nL0xvbmc7TAAIdXNlcm5hbWVxAH4AAXhwAHBwcHBwcHBwcHBwcHBwcHB0AAkxMzMzMzMzMzNwcHBwcHNyABFqYXZhLmxhbmcuQm9vbGVhbs0gcoDVnPruAgABWgAFdmFsdWV4cABwcHBzcgATamF2YS51dGlsLkFycmF5TGlzdHiB0h2Zx2GdAwABSQAEc2l6ZXhwAAAAAHcEAAAAAHhwcHBwdAAEbmFtZQ==");
            System.out.println("strToUser:" + user.toString());
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }
    }
}
