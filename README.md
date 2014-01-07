Ticklist
========

Play! framework test to make a rock climbing ticklist application.

The initial featureset is complete finally. Great learning experience so far!
From here I'm going to jump into the "nice to haves" and play with the UI a
little. I also plan on heading directly to my climbing gym and picking up
my memebership for the next little while! Gonna be great to get back to climbing
and have this ticklist application to keep track of my stuff for me.

JUST COMPLETED:

1) Have restricted deletion to just myself. Should I also restrict adds? Will have to think about that.
Methods throw unauthorized with a message and log details to the console.

2) Cleaned up UI even further.

3) Created a sign-up page using POST / form submission (similar to login) rather than AJAX/JSON as with all
other methods.

4) Secured javascript routes. Bad oversight on my part.

NEXT STEPS:

1) Add boulders to make the application useful.

2) Comment my code and clean it the hell up.

3) Look into the UI a little more to spice it up.

NICE TO HAVE:

1) Possibly a search?

2) Wishlist, kind of thing? Somewhere in between the big list and the ticks.

NOTES:

1) Modeling mistake: ALWAYS use Long id for id's rather than the name.
   A property of the climb/location that may need to be edited after
   the fact should NOT be the ID. Terrible rookie mistake but a good
   thing to learn early before I get into anything important.

2) Coffeescript is not fun. I'd rather write AJAX myself and I think
   I will from now on. I don't know if there's any remaining artifacts
   from trying to use Coffeescript in this application but if there
   is, be assured I have no intention for it to be there.
