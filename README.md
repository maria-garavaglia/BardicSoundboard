# Bardic Soundboard

POC: [Maria Garavaglia](mailto:maria.renie.garavaglia@gmail.com)

This is a customizable soundboard originally meant to accompany Bard spells in a D&D campaign. It supports saving and loading of multiple characters, as well as multiple spells per character. There are two versions of this application: this one uses JavaFX, while the other version uses Swing.

## Building/Running

Requires Java 17+ and Maven to run. Building and running happens in one step, simply run `mvn clean javafx:run` to start.

Saving and loading of characters is done through the File menu. There is a sample character (Hrothgar Hammerfall) in `Characters/`, as well as some sample audio files in `Audio/`.

Double-click a spell to play it, or use the Play/Stop and volume controls at the bottom. Playing a new spell should stop any spells currently playing.

## Programming Notes

This is the older of the two versions, originally written around 2021. I made a few updates after making the Swing version so they align a bit more. In future updates I'd likely swap out json-simple for Jackson and try to make file management a bit more robust and flexible for the user. I do love JavaFX and especially FXML, but I think I need to play around with it more before I'm as comfortable with it as I am with Swing.

