#If we're not in the main directory we'll move there

if [ ! -f "build.gradle" ]; then
    cd ../
fi

cleanProject () {
    rm -rf "$1/.settings"
    rm -rf "$1/bin"
    rm -rf "$1/.classpath"
    rm -rf "$1/.classpath.bak"
    rm -rf "$1/.project"
    rm -rf "$1/.DS_Store"
}

cleanProject "framework";
cleanProject "layers/Layer_1_6";
cleanProject "layers/Layer_1_8";
cleanProject "layers/Layer_2_6";
cleanProject "layers/Layer_2_7";
cleanProject "layers/Layer_2_8_2";
cleanProject "layers/Layer_2_8_6";
cleanProject "layers/Layer_2_8_7";

gradle clean;
