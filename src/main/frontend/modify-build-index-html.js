const fs = require('fs');
const path = require('path');

// Correct the path to the built index.html file
const filePath = path.join(__dirname, 'build', 'index.html');

fs.readFile(filePath, 'utf8', (err, data) => {
    if (err) {
        return console.error('Error reading the file:', err);
    }


    // Apply the necessary replacements
    let modifiedData = data
        .replace(/<title>.*<\/title>/, '<title>AttendanceManager</title>')
        .replace(
            /<script defer="defer" src="\/static\/js\/main\.[a-z0-9]+\.js"><\/script>/,
            '<script defer="defer" src="/AttendanceManager/static/js/main.6056a5f7.js"></script>'
        )
        .replace(
            /<link href="\/static\/css\/main\.[a-z0-9]+\.css" rel="stylesheet">/,
            '<link href="/AttendanceManager/static/css/main.4909cca0.css" rel="stylesheet">'
        )
        .replace(
            /<link rel="manifest" href="\/manifest\.json"\/>/,
            '<link rel="manifest" href="/AttendanceManager/manifest.json"/>'
        );


    // Write the modified content back to the file
    fs.writeFile(filePath, modifiedData, 'utf8', (err) => {
        if (err) {
            return console.error('Error writing the file:', err);
        }
        console.log('The file has been successfully modified!');
    });
});
