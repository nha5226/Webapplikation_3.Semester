//Hier wird eine neue Karte erstellt
const map = L.map('map');

//Hier wird die Ansicht der Karte auf den aktuellen Standort eingestellt
map.locate({setView: true, maxZoom: 16});

function onLocationFound(e) {
}

map.on('locationfound', onLocationFound);

//Hier wird der Karte eine neue Ebene mit Kacheln hinzugefügt (Hier wird auch die "Basiskarte" erstellt, die der Benutzer sieht
L.tileLayer('https://api.mapbox.com/styles/v1/{id}/tiles/{z}/{x}/{y}?access_token={accessToken}', {
    attribution: 'Map data &copy; <a href="https://www.openstreetmap.org/copyright">OpenStreetMap</a> contributors, Imagery :copyright: <a href="https://www.mapbox.com/">Mapbox</a>',
    maxZoom: 25,
    id: 'mapbox/streets-v11',
    tileSize: 512,
    zoomOffset: -1,
    accessToken: 'pk.eyJ1Ijoic3NhNzA5MCIsImEiOiJja3hieTl4OHE0Ymt2MnhsYTN0bjFoZHQ0In0.Ql0ZNUnFxK-hXRe0WoSCyA'
}).addTo(map);
//Hier wird es zur Karte hinzugefügt

//Hier wird die Liste der Ereignisse von der Seite erhalten
$.get('https://api.rss2json.com/v1/api.json?rss_url=https://www.festivalticker.de/rss-festivalfeed/festivalkalender1-60.xml', function (response) {

    //Hier werden die Elemente durchlaufen
    $.each(response.items, function (index, item) {
        //Hier wird ein regulärer Ausdruck für die Ortsangabe extrahiert
        const regExp = /Ort: \d+ ([\wöäüÖÄÜß]*)<br>Land: ([\wöäüÖÄÜß]*)/gm;   // \d+ für mehrere Ziffern für PLZ; ([\wöäüÖÄÜß]*) = [a-zA-Z0-9_] beliebig oft
        //Hier wird der Ausdruck verwendet, um die Ortsangabe in der Beschreibung des Events zu finden
        const locationIsMatching = regExp.exec(item.description);
        //Hier wird das Event erstellt
        const event = $('<div>');
        event.html(`<h2>${item.title}</h2><p>${item.description}</p></div><hr>`);

        //Hier wird geschaut ob die Ortsangaben gefunden wurde, wenn ja... wird die Funktion createMarker() aufgerufen
        if (locationIsMatching) {
            event.attr('event', index);

            event.on('click', () => {
                createMarker(item.title, index, `${locationIsMatching[1]} ${locationIsMatching[2]}`);
            });
            //Hier wird das Ergebnis-Element an das Element .eventContainer eingefügt
            $('.eventContainer').append(event);
        }
        //Falls es nicht findet, soll ein alert kommen
        else if (locationIsMatching === null) {
            console.log(locationIsMatching);
            console.log(item.description);
            alert("Something is wrong with the coordinates!");

        }
    });
});

//Hier wird die Funktion createMarker()
function createMarker(title, eventId, locationEvent) {
    //Hier wird eine URL erstellt für einen API-Aufruf an die OpenStreetMap Nominatim API, um den Breiten- und Längengrad des Ortes des Ereignisses zu erhalten
    const url = "https://nominatim.openstreetmap.org/search?q=" + locationEvent + "&format=json&limit=1";
    //hier wird der API-Aufruf emacht
    $.get(url, function (response) {
        //Hier wird der Finger erstellt
        var greenIcon = L.icon({
            iconUrl: 'finger.png',
            iconSize: [38, 95], // size of the icon
            shadowSize: [50, 64], // size of the shadow
            iconAnchor: [22, 94], // point of the icon which will correspond to marker's location
            popupAnchor: [-3, -76] // point from which the popup should open relative to the iconAnchor
        });

        //Hier wird der Längen und Breitengrad des Ortes aus der API-Antwort abgerufen
        const eventLocation = response[0];
        const eventLatitude = eventLocation.lat;
        const eventLongitude = eventLocation.lon;

        //Hier wird der marker erstellt den man auf der Karte am bestimmten Ort angezeigt
        const eventMarker = L.marker([eventLatitude, eventLongitude], {icon: greenIcon}).addTo(map);
        //Hier wird das Popup hinzugefügt
        eventMarker.bindPopup(`<b>${title}</b><br>${locationEvent}`);
        //und hier wird das PopUp geöffnet
        eventMarker.openPopup();
        //Hier "fliegt" die Karte zum entsprechenden Ort
        map.flyTo([eventLatitude, eventLongitude], 13);
        const eventContainer = $('<div>');
        eventContainer.html(`<h2>${item.title}</h2><p>${item.description}</p></div><hr>`);
    });
}
