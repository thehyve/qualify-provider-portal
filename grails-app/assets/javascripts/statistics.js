//= require jquery

if( typeof(QSP) == 'undefined' )
	QSP = {};

QSP.Statistics = {
	init: function() {
		var titles = $( ".stats li h2" );

		// Enable clicking on webservice names to load data
		titles.on( "click", function() {
			var div = $(this).parent().find( ".statistics" );

			if( !div.data( "loaded" ) ) {
				QSP.Statistics.load(div, $(this));
			}

			return false;
		});

		// load data if there is only a single webservice available
		if( titles.length == 1 ) {
			titles.first().trigger( "click" );
		}

		// Make sure to reload statistics graphs when form changes
		$( "#stats-form" ).on( "submit", function() {
			QSP.Statistics.reload();
			return false;
		});
	},

	load: function(element, h2) {
		// Find the URL to load data from, and add the parameters needed
		var url = element.data( "url" );
		url += "&" + $( '#stats-form' ).serialize();

		// Add spinner to the div
		h2.append( Utils.spinner() );

		// Clear the element (in case of reloading)
		element.empty();

		$.get(url)
			.done(function(data) {
				// Mark the element as being loaded. The class will increase the height of the container
				// which is needed to draw the chart
				element.data("loaded", true).addClass( "loaded");

			  	$.plot( element,
					  data.statistics,
					  { xaxis: { mode: 'time',
								 timeformat: data.timeFormat,
								 monthNames: ["Jan", "Feb", "Mar", "Apr", "May", "Jun", "Jul", "Aug", "Sep", "Oct", "Nov", "Dec"]} } );

			})
			.fail(function(jqXHR) {
				Utils.message( "Error loading statistics for this webservice: " + jqXHR.responseText, "alert-danger" );
			})
			.always(function() {
				h2.find( ".spinner" ).remove();
			});
	},

	reload: function() {
		$( ".stats li h2" ).each(function(idx, el) {
			var h2 = $(el);
			var div = h2.parent().find( ".statistics" );

			// Only reload charts that had been loaded already
			if( div.data( "loaded" ) ) {
				QSP.Statistics.load(div, h2);
			}
		});
	}

};

$(QSP.Statistics.init);
