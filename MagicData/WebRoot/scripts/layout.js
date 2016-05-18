// topology_logic.js v0.0.1 alpha
// Author: xqw
// Date: 2015-08-19 created

  	var nodeSet;
    var edgeSet;
    var cy;
    var opts_set = {};
    var init_layout = function(){
	    opts_set['cose'] = {
        name: 'cose',
        // Called on `layoutready`
        ready               : function() {},
        // Called on `layoutstop`
        stop                : on_layout_done,
        // Whether to animate while running the layout
        animate             : true,
        // The layout animates only after this many milliseconds
        // (prevents flashing on fast runs)
        animationThreshold  : 250,
        // Number of iterations between consecutive screen positions update
        // (0 -> only updated on the end)
        refresh             : 20,
        // Whether to fit the network view after when done
        fit                 : true,
        // Padding on fit
        padding             : 30,
        // Constrain layout bounds; { x1, y1, x2, y2 } or { x1, y1, w, h }
        boundingBox         : undefined,
        // Extra spacing between components in non-compound graphs
        componentSpacing    : 30,
        // Node repulsion (non overlapping) multiplier
        nodeRepulsion       : function( node ){ return 400000; },
        // Node repulsion (overlapping) multiplier
        nodeOverlap         : 10,
        // Ideal edge (non nested) length
        idealEdgeLength     : function( edge ){ return 10; },
        // Divisor to compute edge forces
        edgeElasticity      : function( edge ){ return 100; },
        // Nesting factor (multiplier) to compute ideal edge length for nested edges
        nestingFactor       : 5,
        // Gravity force (constant)
        gravity             : 80,
        // Maximum number of iterations to perform
        numIter             : 100000,
        // Initial temperature (maximum node displacement)
        initialTemp         : 200,
        // Cooling factor (how the temperature is reduced between consecutive iterations
        coolingFactor       : 0.95,
        // Lower temperature threshold (below this point the layout will end)
        minTemp             : 1.0,
        // Whether to use threading to speed up the layout
        useMultitasking     : false,
        debug               : false
	    };

	    opts_set['random'] = {
        name: 'random',

        fit: true, // whether to fit to viewport
        padding: 30, // fit padding
        boundingBox: undefined, // constrain layout bounds; { x1, y1, x2, y2 } or { x1, y1, w, h }
        animate: false, // whether to transition the node positions
        animationDuration: 500, // duration of animation in ms if enabled
        animationEasing: undefined, // easing of animation if enabled
        ready: undefined, // callback on layoutready
        stop                : on_layout_done
      };

      opts_set['grid'] = {
        name: 'grid',

        fit: true, // whether to fit the viewport to the graph
        padding: 30, // padding used on fit
        boundingBox: undefined, // constrain layout bounds; { x1, y1, x2, y2 } or { x1, y1, w, h }
        avoidOverlap: true, // prevents node overlap, may overflow boundingBox if not enough space
        avoidOverlapPadding: 10, // extra spacing around nodes when avoidOverlap: true
        condense: false, // uses all available space on false, uses minimal space on true
        rows: undefined, // force num of rows in the grid
        cols: undefined, // force num of columns in the grid
        position: function( node ){}, // returns { row, col } for element
        sort: undefined, // a sorting function to order the nodes; e.g. function(a, b){ return a.data('weight') - b.data('weight') }
        animate: false, // whether to transition the node positions
        animationDuration: 500, // duration of animation in ms if enabled
        animationEasing: undefined, // easing of animation if enabled
        ready: undefined, // callback on layoutready
        stop: on_layout_done // callback on layoutstop			
      };

      opts_set['circle'] = {
        name: 'circle',

        fit: true, // whether to fit the viewport to the graph
        padding: 30, // the padding on fit
        boundingBox: undefined, // constrain layout bounds; { x1, y1, x2, y2 } or { x1, y1, w, h }
        avoidOverlap: true, // prevents node overlap, may overflow boundingBox and radius if not enough space
        radius: undefined, // the radius of the circle
        startAngle: 3/2 * Math.PI, // where nodes start in radians
        sweep: undefined, // how many radians should be between the first and last node (defaults to full circle)
        clockwise: true, // whether the layout should go clockwise (true) or counterclockwise/anticlockwise (false)
        sort: undefined, // a sorting function to order the nodes; e.g. function(a, b){ return a.data('weight') - b.data('weight') }
        animate: false, // whether to transition the node positions
        animationDuration: 500, // duration of animation in ms if enabled
        animationEasing: undefined, // easing of animation if enabled
        ready: undefined, // callback on layoutready
        stop: on_layout_done // callback on layoutstop
      };

      opts_set['concentric']  = {
        name: 'concentric',

        fit: true, // whether to fit the viewport to the graph
        padding: 30, // the padding on fit
        startAngle: 8 * Math.PI, // where nodes start in radians
        sweep: undefined, // how many radians should be between the first and last node (defaults to full circle)
        clockwise: true, // whether the layout should go clockwise (true) or counterclockwise/anticlockwise (false)
        equidistant: false, // whether levels have an equal radial distance betwen them, may cause bounding box overflow
        minNodeSpacing: 60, // min spacing between outside of nodes (used for radius adjustment)
        boundingBox: undefined, // constrain layout bounds; { x1, y1, x2, y2 } or { x1, y1, w, h }
        avoidOverlap: true, // prevents node overlap, may overflow boundingBox if not enough space
        height: undefined, // height of layout area (overrides container height)
        width: undefined, // width of layout area (overrides container width)
        concentric: function(node){ // returns numeric value for each node, placing higher nodes in levels towards the centre
        return node.degree();
        },
        levelWidth: function(nodes){ // the variation of concentric values in each level
        return nodes.maxDegree() / 4;
        },
        animate: false, // whether to transition the node positions
        animationDuration: 500, // duration of animation in ms if enabled
        animationEasing: undefined, // easing of animation if enabled
        ready: undefined, // callback on layoutready
        stop: on_layout_done // callback on layoutstop
      };	

      opts_set['breadthfirst'] = {
        name: 'breadthfirst',

        fit: true, // whether to fit the viewport to the graph
        directed: false, // whether the tree is directed downwards (or edges can point in any direction if false)
        padding: 30, // padding on fit
        circle: false, // put depths in concentric circles if true, put depths top down if false
        spacingFactor: 1.75, // positive spacing factor, larger => more space between nodes (N.B. n/a if causes overlap)
        boundingBox: undefined, // constrain layout bounds; { x1, y1, x2, y2 } or { x1, y1, w, h }
        avoidOverlap: true, // prevents node overlap, may overflow boundingBox if not enough space
        roots: undefined, // the roots of the trees
        maximalAdjustments: 0, // how many times to try to position the nodes in a maximal way (i.e. no backtracking)
        animate: false, // whether to transition the node positions
        animationDuration: 500, // duration of animation in ms if enabled
        animationEasing: undefined, // easing of animation if enabled
        ready: undefined, // callback on layoutready
        stop: on_layout_done // callback on layoutstop
      };
    }

	
  	var on_layout_done = function() {
	    nodes.forEach(function(node){
	      	// var lp = window.graph.toLogical(nodeSet[node].position('x')*window.graph.scale,nodeSet[node].position('y')*window.graph.scale);
          // node.setLocation(lp.x,lp.y);
          node.x = nodeSet[node.uuid].position("x");
          node.px = nodeSet[node.uuid].position("x");
          node.y = nodeSet[node.uuid].position("y");
          node.py = nodeSet[node.uuid].position("y");
          /*node.x = cy.getElementById(node.id).position("x");
          node.px = cy.getElementById(node.id).position("x");
          node.y = cy.getElementById(node.id).position("y");
          node.py = cy.getElementById(node.id).position("y");*/
          graphDrawUpdate();
          svg.selectAll("g.node").each(function(d){
	          d.fixed = true;
	        });
	      });
      cy.destroy();
      var previous_clone_canvas = document.getElementById('clone_svg')
      if(previous_clone_canvas){
        previous_clone_canvas.parentNode.removeChild(previous_clone_canvas);
      }
  	}
  	
    var on_layout = function(name) {
      if(opts_set[name]){
        var options = opts_set[name];
        var gap;
        var clientWidth = $(".displayDiv svg").attr("width");//document.getElementById('canvas').clientWidth;
        var clientHeight = $(".displayDiv svg").attr("height");//document.getElementById('canvas').clientHeight;
        var my_clone_element = document.getElementById('displaySvg').cloneNode(true);//$("#initsvg").cloneNode(true);
        my_clone_element.id="clone_svg";
        my_clone_element.style.display = "none";
        nodeSet = {};
        edgeSet = {};

        document.getElementById('displaySvg').parentNode.appendChild(my_clone_element);
        // create node and edges
        cy = cytoscape({
          container: my_clone_element,
          renderer: null,
          headless: false,
          clientWidth : clientWidth,
          clientHeight : clientHeight
        });

        //var graph = new Springy.Graph();
        nodeSet = {};
        edgeSet = {};

        // nodes
        nodes.forEach(function(node){
          var newNode = cy.add({
                group: "nodes",
                data: { node: node,id:node.uuid,weight: 75 },
                //position: { x: 200, y: 200 }
            });
          nodeSet[node.uuid] = newNode;
        });
        
        edges.forEach(function(edge){
          var newLink = cy.add({
                group: "edges",
                data: { edge: edge, id : edge.uuid, source: edge.source.uuid, target: edge.target.uuid}
            });
          edgeSet[edge.uuid] = newLink;
        });
        
        
        // window.graph.forEach(function(edge){
          
              // var newEdge = cy.add({
                  // group: "edges",
                  // data: { edge: edge,id:edge.get("id"),source: edge.from.get("id"), target: edge.to.get("id") }
              // });
              // edgeSet[edge] = newEdge;
          
        // });
        
        
        var layout = cy.makeLayout(options);
        layout.run();
      }
    }
