import java.util.ArrayList;
import java.util.List;

class MyString {

    public static String[] removeNulls(String[] strings) {
        List<String> temp = new ArrayList< >();
        for (String string : strings) {
            if (!string.equals("")) {
                temp.add(string);
            }
        }
        return   temp.toArray(new String[temp.size()]);

    }

    /**
     * here string spilt by entry string but in this method the target string is included
     *
     * @param string string
     * @return the spilt string
     */
    public static String[] splitStringInclude(String string, String... target) {
        List<String> ans = new ArrayList<>();
        if (string.contains("\u2230")) {
            for (String targetTemp : target) string = string.replaceAll(targetTemp, "\u2230" + targetTemp + "\u2230");
            return string.split("\u2230");
        } else {
            int indexAns = 0;
            for (int indexString = 1; indexString < string.length(); indexString++) {
                String tempString = string.substring(0, indexString);
                for (String targetTemp : target)
                    if (tempString.contains(targetTemp)) {
                        ans.add(indexAns, string.split(targetTemp)[0]);
                        indexAns++;
                        ans.add(indexAns, targetTemp);
                        indexAns++;
                        string = string.split(targetTemp, 2)[1];
                        indexString = 1;
                        break;
                    }
            }
            ans.add(indexAns, string);
        }


        return ans.toArray(new String[0]);
    }
}
