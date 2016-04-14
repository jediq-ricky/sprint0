#Sprint 0

Sprint Zero, an opinionated constructor for development project toolsets.

Very, VERY, early Alpha, ie it doesn't work yet.  But it's a start and it's [got a website](http://sprint0.io).

##Documentation

The documentation is written using the [Markdown](http://daringfireball.net/projects/markdown/) format.  Diagrams are produced using [PlantUML](http://plantuml.sourceforge.net/).

The object diagrams (also written in PlantUML) require the open sourced [GraphViz](http://www.graphviz.org/) to be installed on the local machine.  If this is not installed then an errored image is displayed.  This can be installed on a mac using `brew install graphviz`.

The documentation is transformed into a mini (web)site using the maven site plugin.  To generate the site, including the diagrams and serve to port `8000` locally (on OSX) use the following command:

	mvn clean site ; pushd . ; cd target/site ; python -m SimpleHTTPServer ; popd
	
