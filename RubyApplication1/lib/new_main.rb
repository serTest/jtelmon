# 
# http://www.troubleshooters.com/codecorn/ruby/database/index.htm 
#
#!/usr/bin/ruby
puts "Hello World"
require 'dbi'

def connect_to_mysql()
	puts "\nConnecting to MySQL..."
	#return DBI.connect("dbi:Mysql:test:localhost", "testuser", "testpass")

	return DBI.connect('DBI:Mysql:test', 'myid', '')
end

def connect_to_postgres()
	puts "\nConnecting to Postgres..."
	return DBI.connect('DBI:Pg:test', 'myid', '')
end

def exercise_database(dbh)
	query = dbh.prepare("SELECT * FROM rocks")
	query.execute()

	while row = query.fetch() do
		puts row
	end
end

def main()
	dbh = connect_to_mysql()
	exercise_database(dbh)
	dbh.disconnect

	dbh = connect_to_postgres()
	exercise_database(dbh)
	dbh.disconnect
end

main()
900