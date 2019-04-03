function initMap() {

    var lat = 35.16273177463202;
    var lng = 129.04514129669803;

    var origin = {lat: lat, lng: lng};

    var map = new google.maps.Map(document.getElementById('map'), {
        zoom: 15,
        center: origin
    });
    var marker = new google.maps.Marker({
        position: origin,
        map: map,
        title: 'Hello World!'
    });
    var clickHandler = new ClickEventHandler(map, origin);
}


function initAutocomplete() {
    var lat = 35.16273177463202;
    var lng = 129.04514129669803;
    var map = new google.maps.Map(document.getElementById('map'), {
        center: {lat: lat, lng: lng},
        zoom: 13,
    });
    var input = document.getElementById('pac-input');
    var searchBox = new google.maps.places.SearchBox(input);
    map.controls[google.maps.ControlPosition.TOP_LEFT].push(input);
    map.addListener('bounds_changed', function() {
        searchBox.setBounds(map.getBounds());
    });
    var markers = [];
    searchBox.addListener('places_changed', function() {
        var places = searchBox.getPlaces();

        if (places.length == 0) {
            return;
        }
        markers.forEach(function(marker) {
            marker.setMap(null);
        });
        markers = [];
        var bounds = new google.maps.LatLngBounds();

        console.log(places[0].geometry);
        console.log(places[0].geometry.viewport);
        console.log(places[0].geometry.viewport.ma);

        var lat = places[0].geometry.viewport.ma.j;
        var lng = places[0].geometry.viewport.ga.j;
        // $('.inputs_lat').val(lat);
        // $('.inputs_lng').val(lng);
        // $('#pac-input').val(places[0].formatted_address);
        $('#pac-input').val(places[0].formatted_address);


        places.forEach(function(place) {
            if (!place.geometry) {
                return;
            }
            // var icon = {
            //     url: '/static/image/study/marker.gif',
            //     size: new google.maps.Size(60, 60),
            //     origin: new google.maps.Point(0, 0),
            //     anchor: new google.maps.Point(17, 34),
            //     scaledSize: new google.maps.Size(50, 50)
            // };

            // Create a marker for each place.
            markers.push(new google.maps.Marker({
                map: map,
                title: place.name,
                position: place.geometry.location
            }));

            if (place.geometry.viewport) {
                // Only geocodes have viewport.
                bounds.union(place.geometry.viewport);
            } else {
                bounds.extend(place.geometry.location);
            }
        });
        map.fitBounds(bounds);
    });


    var clickHandler = new ClickEventHandler(map, origin);
}



var ClickEventHandler = function(map, origin) {
    this.origin = origin;
    this.map = map;
    this.directionsService = new google.maps.DirectionsService;
    this.directionsDisplay = new google.maps.DirectionsRenderer;
    this.directionsDisplay.setMap(map);
    this.placesService = new google.maps.places.PlacesService(map);
    this.infowindow = new google.maps.InfoWindow;
    this.infowindowContent = document.getElementById('infowindow-content');
    this.infowindow.setContent(this.infowindowContent);

    this.map.addListener('click', this.handleClick.bind(this));
};


ClickEventHandler.prototype.handleClick = function(event) {
    var latlngStr = event.latLng.toString();
    var latlng = latlngStr.substring(1,latlngStr.length-1);
    latlng = latlng.split(',');
    var lat = latlng[0];
    var lng = latlng[1].substring(1,latlng[1].length);
    if (event.placeId) {
        event.stop();
        this.calculateAndDisplayRoute(event.placeId);
        this.getPlaceInformation(event.placeId);
    }
};

ClickEventHandler.prototype.calculateAndDisplayRoute = function(placeId) {
    var me = this;
    this.directionsService.route({
        origin: this.origin,
        destination: {placeId: placeId},
        travelMode: 'WALKING'
    }, function(response, status) {
        if (status === 'OK') {
            me.directionsDisplay.setDirections(response);
        } else {
            window.alert('Directions request failed due to ' + status);
        }
    });
};

ClickEventHandler.prototype.getPlaceInformation = function(placeId) {
    var me = this;
    this.placesService.getDetails({placeId: placeId}, function(place, status) {
        if (status === 'OK') {
            me.infowindow.close();
            me.infowindow.setPosition(place.geometry.location);
            me.infowindowContent.children['place-icon'].src = place.icon;
            me.infowindowContent.children['place-name'].textContent = place.name;
            me.infowindowContent.children['place-id'].textContent = place.place_id;
            me.infowindowContent.children['place-address'].textContent =
                place.formatted_address;
            me.infowindow.open(me.map);
        }
    });
};