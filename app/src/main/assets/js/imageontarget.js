var World = {
    markerDrawable_idle: null,
    markerDrawable_selected: null,
    markerDrawable_directionIndicator: null,

	loaded: false,

	init: function initFn() {
		this.createOverlays();
	},

	createOverlays: function createOverlaysFn() {
        World.markerDrawable_idle = new AR.ImageResource("assets/marker_idle.png");
        World.markerDrawable_selected = new AR.ImageResource("assets/marker_selected.png");
        World.markerDrawable_directionIndicator = new AR.ImageResource("assets/indi.png");

		/*
			First an AR.ClientTracker needs to be created in order to start the recognition engine. It is initialized with a URL specific to the target collection. Optional parameters are passed as object in the last argument. In this case a callback function for the onLoaded trigger is set. Once the tracker is fully loaded the function worldLoaded() is called.

			Important: If you replace the tracker file with your own, make sure to change the target name accordingly.
			Use a specific target name to respond only to a certain target or use a wildcard to respond to any or a certain group of targets.
		*/
		this.tracker = new AR.ClientTracker("assets/burke.wtc", {
			onLoaded: this.worldLoaded
		});

		/*
			The next step is to create the augmentation. In this example an image resource is created and passed to the AR.ImageDrawable. A drawable is a visual component that can be connected to an IR target (AR.Trackable2DObject) or a geolocated object (AR.GeoObject). The AR.ImageDrawable is initialized by the image and its size. Optional parameters allow for position it relative to the recognized target.
		*/
		var poiData = myJsonData;
		for (var targets = 0; targets < poiData.length; targets++) {
    		this.createDroplet(poiData[targets].name);
    	}
	},

    createDroplet: function createDropletFn(name) {
        /* Create overlay for page one */
        this.imgOne = new AR.ImageResource("assets/marker_idle.png");
        var overlayOne = new AR.ImageDrawable(this.imgOne, 0.3, {
            offsetX: -0.15,
            offsetY: -0.15,
            zOrder: 0
        });

        var htmlDrawable = new AR.HtmlDrawable({
            uri: "assets/pin.html"
        }, 0.3, {
            offsetX: -0.15,
            offsetY: -0.15,
            zOrder : 1,
            onClick : function() {
                document.location = "architectsdk://markerselected?title=" + name;
            },
        });

        /*
            The last line combines everything by creating an AR.Trackable2DObject with the previously created tracker, the name of the image target and the drawable that should augment the recognized image.
            Please note that in this case the target name is a wildcard. Wildcards can be used to respond to any target defined in the target collection. If you want to respond to a certain target only for a particular AR.Trackable2DObject simply provide the target name as specified in the target collection.
        */
        var pageOne = new AR.Trackable2DObject(this.tracker, name, {
            drawables: {
                cam: [ overlayOne, htmlDrawable ]
            }
        });
    },

	worldLoaded: function worldLoadedFn() {
		var cssDivLeft = " style='display: table-cell;vertical-align: middle; text-align: right; width: 50%; padding-right: 15px;'";
		var cssDivRight = " style='display: table-cell;vertical-align: middle; text-align: left;'";
		document.getElementById('loadingMessage').innerHTML =
			"<div" + cssDivLeft + ">Move camera to scan artifacts!</div>";

		// Remove Scan target message after 10 sec.
		setTimeout(function() {
			var e = document.getElementById('loadingMessage');
			e.parentElement.removeChild(e);
		}, 10000);
	}
};

World.init();
