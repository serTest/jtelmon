# 
# To change this template, choose Tools | Templates
# and open the template in the editor.
 

puts "Hello World"

#!/usr/bin/ruby
require 'rubygems'
require_gem 'activerecord'

ActiveRecord::Base.establish_connection(
#  :adapter  => "mysql",
#  :adapter  => "pg",        # adapter not found error, wrong name
  :adapter=>"postgresql",
  :database => "test"
)

class Rock < ActiveRecord::Base
end

puts
puts "Let's add Bassalt to the list of rocks!"
newrow = Rock.new
newrow.rockname = "Bassalt"
newrow.save
puts
puts "Now let's see the first rock!"
puts Rock.find(:first).rockname
puts
puts "Now let's see all the rocks!"
queryresults = Rock.find(:all)
queryresults.each do |row|
	print row.id, "   ", row.rockname, "\n"
end

puts
puts "Let's delete Bassalt from the list of rocks!"
Rock.delete_all("rockname = 'Bassalt'")
puts
puts "Now let's view the revised list of rocks!"
queryresults = Rock.find(:all)
queryresults.each do |row|
	print row.id, "   ", row.rockname, "\n"
end