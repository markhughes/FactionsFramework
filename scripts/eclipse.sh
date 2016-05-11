#If we're not in the main directory we'll move there

if [ ! -f "plugin.yml" ]; then
    cd ../
fi

buildLayer () {
    cd "layers/$1";

    gradle eclipse;

    # Gradles eclipse plugin adds weird slashes so this makes that a bit more tidy
    #sed -i "" "s/<name>layers\/$1</name>/<name>FactionsFramework $1</name>/g" .project

    # fix the classpath for FactionsFramework
    #sed -i "" "s/<classpathentry kind=\"src\" path=\"\/framework\"\/>/<classpathentry kind=\"src\" path=\"FactionsFramework\"\/>/g" .classpath

    cd ../../

}

buildFramework() {
    cd framework;
    gradle eclipse;

    cd ../
}

buildFramework;

buildLayer "Layer_1_6";
buildLayer "Layer_1_8";
buildLayer "Layer_2_6";
buildLayer "Layer_2_7";
buildLayer "Layer_2_8_2";
buildLayer "Layer_2_8_6";
buildLayer "Layer_2_8_7";
