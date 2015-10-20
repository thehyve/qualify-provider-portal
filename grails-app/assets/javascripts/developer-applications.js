//= require jquery
var QSP = {
	Developers: {
		init: function() {
			var titles = $( ".applications li h2" );

			// Enable clicking on webservice names to load data
			titles.on( "click", function() {
				var div = $(this).parent().find( ".applicationList" );

				if( !div.data( "loaded" ) ) {
 					QSP.Developers.load(div, $(this));
 				}

 				return false;
			});

			// load data if there is only a single webservice available
			if( titles.length == 1 ) {
				titles.first().trigger( "click" );
			}
		},

		load: function(element, h2) {
			// Load data for the given URL
			var url = element.data( "url" );

			// Add spinner to the div
			h2.append( QSP.spinner() );

			$.get(url)
				.done(function(data) {
					// Create a table with data
					var table = $( "<table>" ).addClass( "table table-striped" );
					table.append( $( "<thead>" ).append(
						$("<tr>" )
							.append( $("<th>").text( "App name" ) )
							.append( $("<th>").text( "App description" ) )
							.append( $("<th>").text( "Status" ) )
							.append( $("<th>").text( "Last updated" ) )
							.append( $("<th>").text( "" ) )
					));

					var tbody = $( "<tbody>" ).appendTo(table);

					// Add a row for each developer
					$.each(data, function(idx, el) {
						tbody.append(
							$("<tr>")
								.addClass( "state-" + el.state )
								.append( $("<td>").text( el.name ) )
								.append( $("<td>").text( el.description ) )
								.append( $("<td>").text( el.state ).addClass( "state" ) )
								.append( $("<td>").text( el.updated_at ) )
								.append( $("<td>").append( $( "<button>" ) ) )
						);
					});

					// Attach the table to the element
					element.append(table);

					// Mark the element as being loaded
					element.data("loaded", true);
				})
				.fail(function(jqXHR, textStatus) {
					element.text( "Error loading developer list for this webservice: " + textStatus );
				})
				.always(function() {
					h2.find( ".glyphicon-spin" ).remove();
				});
		}
	},

	spinner: function() {
		return '<span class="glyphicon glyphicon-refresh glyphicon-spin"></span>';
	}
}

$(QSP.Developers.init);
