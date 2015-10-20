//= require jquery
//= require tables
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
					var table = Table.create();
					table.find( "thead" ).append( Table.headerRow( [ "App name", "App description", "Status", "Plan", "Last updated", "" ] ) );

					var tbody = table.find( "tbody" );

					// Add a row for each developer
					$.each(data, function(idx, el) {
						tbody.append(
							$("<tr>")
								.data( "url", el.details_url )
								.addClass( "state-" + el.state )
								.append( $("<td>").append( $( "<a href='#'>" ).text(el.name).on( "click", function() { QSP.Developers.showApplicationInfo(this); return false; } ) ) )
								.append( $("<td>").text( el.description ).addClass( "description ellipsis" ).attr( "title", el.description ) )
								.append( $("<td>").text( el.state ).addClass( "state" ) )
								.append( $("<td>").text( el.plan.name ).addClass( "application_plan" ) )
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
					h2.find( ".spinner" ).remove();
				});
		},

		/**
		 * Shows detailed information about an application in a modal dialog
		 */
		showApplicationInfo: function(element) {
			var $element = $(element);
			var row = $element.closest( "tr" );
			var url = row.data( "url" );

			// Add spinner to the name
			$element.append( QSP.spinner() );

			// Create modal dialog
			var dialog = $('#modal-application-info');

			$.get(url)
				.done(function(data) {
					dialog.find( ".modal-title" ).text(data.name);

					// Create table with information
					var table = Table.create();
					var tbody = table.find( "tbody" );

					tbody.append( Table.row( [ "Application name", data.name] ) );
					tbody.append( Table.row( [ "Application description", data.description] ) );
					tbody.append( Table.row( [ "Application plan", data.plan.name ] ) );

					tbody.append( Table.row( [ " ", " " ] ) );

					tbody.append( Table.row( [ "Account name", data.account.org_name] ) );
					tbody.append( Table.row( [ "User email", data.account.users[0].email ] ) );
					tbody.append( Table.row( [ "Service plan", data.service_plan.name ] ) );

					// Add it to the dialog and show it
					dialog.find( ".modal-body" ).empty().append(table);
					dialog.modal('show')
				})
				.fail(function(jqXHR) {
					dialog.find( ".modal-title" ).text("Error");
					dialog.find( ".modal-body" ).text("Error loading developer list for this webservice: " + jqXHR.responseText);
					dialog.modal('show')
				})
				.always(function() {
					$element.find( ".spinner" ).remove();
				});


		}
	},

	spinner: function() {
		return '<span class="glyphicon glyphicon-refresh glyphicon-spin spinner"></span>';
	}
}

$(QSP.Developers.init);
