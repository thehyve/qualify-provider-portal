databaseChangeLog = {

	changeSet(author: "robert (generated)", id: "1415373772856-1") {
		createTable(tableName: "qualify_user") {
			column(name: "id", type: "int8") {
				constraints(nullable: "false", primaryKey: "true", primaryKeyName: "qualify_userPK")
			}

			column(name: "version", type: "int8") {
				constraints(nullable: "false")
			}

			column(name: "account_expired", type: "boolean") {
				constraints(nullable: "false")
			}

			column(name: "account_locked", type: "boolean") {
				constraints(nullable: "false")
			}

			column(name: "enabled", type: "boolean") {
				constraints(nullable: "false")
			}

			column(name: "password", type: "varchar(255)") {
				constraints(nullable: "false")
			}

			column(name: "password_expired", type: "boolean") {
				constraints(nullable: "false")
			}

			column(name: "username", type: "varchar(255)") {
				constraints(nullable: "false")
			}
		}
	}

	changeSet(author: "robert (generated)", id: "1415373772856-2") {
		createTable(tableName: "registration_code") {
			column(name: "id", type: "int8") {
				constraints(nullable: "false", primaryKey: "true", primaryKeyName: "registration_PK")
			}

			column(name: "date_created", type: "timestamp") {
				constraints(nullable: "false")
			}

			column(name: "token", type: "varchar(255)") {
				constraints(nullable: "false")
			}

			column(name: "username", type: "varchar(255)") {
				constraints(nullable: "false")
			}
		}
	}

	changeSet(author: "robert (generated)", id: "1415373772856-3") {
		createTable(tableName: "role") {
			column(name: "id", type: "int8") {
				constraints(nullable: "false", primaryKey: "true", primaryKeyName: "rolePK")
			}

			column(name: "version", type: "int8") {
				constraints(nullable: "false")
			}

			column(name: "authority", type: "varchar(255)") {
				constraints(nullable: "false")
			}
		}
	}

	changeSet(author: "robert (generated)", id: "1415373772856-4") {
		createTable(tableName: "user_role") {
			column(name: "role_id", type: "int8") {
				constraints(nullable: "false")
			}

			column(name: "user_id", type: "int8") {
				constraints(nullable: "false")
			}
		}
	}

	changeSet(author: "robert (generated)", id: "1415373772856-5") {
		createTable(tableName: "webservice") {
			column(name: "id", type: "int8") {
				constraints(nullable: "false", primaryKey: "true", primaryKeyName: "webservicePK")
			}

			column(name: "version", type: "int8") {
				constraints(nullable: "false")
			}

			column(name: "name", type: "varchar(255)") {
				constraints(nullable: "false")
			}

			column(name: "threescale_id", type: "varchar(255)") {
				constraints(nullable: "false")
			}
		}
	}

	changeSet(author: "robert (generated)", id: "1415373772856-6") {
		addPrimaryKey(columnNames: "role_id, user_id", constraintName: "user_rolePK", tableName: "user_role")
	}

	changeSet(author: "robert (generated)", id: "1415373772856-9") {
		createIndex(indexName: "username_uniq_1415373772804", tableName: "qualify_user", unique: "true") {
			column(name: "username")
		}
	}

	changeSet(author: "robert (generated)", id: "1415373772856-10") {
		createIndex(indexName: "authority_uniq_1415373772808", tableName: "role", unique: "true") {
			column(name: "authority")
		}
	}

	changeSet(author: "robert (generated)", id: "1415373772856-11") {
		createSequence(sequenceName: "hibernate_sequence")
	}

	changeSet(author: "robert (generated)", id: "1415373772856-7") {
		addForeignKeyConstraint(baseColumnNames: "role_id", baseTableName: "user_role", constraintName: "FK_it77eq964jhfqtu54081ebtio", deferrable: "false", initiallyDeferred: "false", referencedColumnNames: "id", referencedTableName: "role", referencesUniqueColumn: "false")
	}

	changeSet(author: "robert (generated)", id: "1415373772856-8") {
		addForeignKeyConstraint(baseColumnNames: "user_id", baseTableName: "user_role", constraintName: "FK_apcc8lxk2xnug8377fatvbn04", deferrable: "false", initiallyDeferred: "false", referencedColumnNames: "id", referencedTableName: "qualify_user", referencesUniqueColumn: "false")
	}

	include file: 'user-webservice-association.groovy'
}
