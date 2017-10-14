function StackLayout(positionX,positionY,width_of_box,height_of_box,direction,horizontalAlign,verticalAlign,gap)
{
	
	this.stackDirection = direction.toUpperCase();//HORIZONTAL, VERTICAL
	this.horizontalAlignment = horizontalAlign.toUpperCase();//LEFT, RIGHT, CENTER
	this.verticalAlignment = verticalAlign.toUpperCase();//TOP MIDDLE BOTTOM
	this.x = positionX;
	this.y = positionY;
	this.elements =[];
	this.index = 0;
	this.gap = gap;
	this.width = width_of_box;
	this.height = height_of_box;
	
	this.getBBox = function()
	{
		return {x:this.x,y:this.y,x2:this.x+this.width,y2:this.y+this.height,width:this.width,height:this.height};
	}

	this.setX = function (value) {
	    var diff = value - this.x;
	    for (var i = 0; i < this.elements.length; i++) {
	        if (this.elements[i] instanceof StackLayout) {
	            this.elements[i].setX(this.elements[i].x + value);
	        }
	        else if (this.elements[i] instanceof EmptySpace) {
	            this.elements[i].x = this.elements[i].x + diff;
	        }
	        else {
	            this.elements[i].attr({ 'x': this.elements[i].attr('x') + diff });
	            

	        }
	    }
	    this.x = value;
	}
	
	this.setY = function(value)
	{
		var diff = value - this.y;
		for(var i=0;i<this.elements.length;i++)
		{
			if( this.elements[i] instanceof StackLayout)
			{
			    this.elements[i].setY(this.elements[i].y + value);
			}
			else if (this.elements[i] instanceof EmptySpace)
			{
				this.elements[i].y = this.elements[i].y + diff;
			}
			else
			{
				this.elements[i].attr({'y':this.elements[i].attr('y')+diff});
			}
		}
		this.y = value;
	}

	this.addElement = function (element) {
	    var posX = 0;
	    var posY = 0;
	    if (this.index == 0) {

	        if (this.horizontalAlignment == 'LEFT') {
	            posX = this.x;
	        }
	        else if (this.horizontalAlignment == 'RIGHT') {
	            posX = (this.x + this.width) - element.getBBox().width;

	        }
	        else if (this.horizontalAlignment == 'CENTER') {
	            posX = (this.x + (this.width / 2)) - (element.getBBox().width / 2);
	        }

	        if (this.verticalAlignment == "TOP") {
	            posY = this.y;
	        }
	        else if (this.verticalAlignment == "BOTTOM") {
	            posY = this.y + this.height - element.getBBox().height;
	        }
	        else if (this.verticalAlignment == "MIDDLE") {
	            posY = this.y + (this.height / 2) - (element.getBBox().height / 2);
	        }



	        if (element instanceof StackLayout) {
	            element.setX(posX);
	            element.setY(posY);
	        }
	        else if (element instanceof EmptySpace) {
	            element.x = posX;
	            element.y = posY;
	        }
	        else {
	            element.attr({ 'x': posX, 'y': posY });
	        }


	    }
	    else {
	        var prevElement = this.elements[this.index - 1];
	        var prevBBox = prevElement.getBBox();

	        if (this.stackDirection == 'HORIZONTAL') {
	            if (this.horizontalAlignment == 'LEFT') {
	                posX = prevBBox.x2 + this.gap;
	            }
	            else if (this.horizontalAlignment == 'RIGHT') {
	                posX = prevBBox.x - this.gap - element.getBBox().width;
	            }
	            else if (this.horizontalAlignment == 'CENTER') {
	                var shiftBy = (element.getBBox().width + this.gap) / 2;
	                for (var i = 0; i < this.elements.length; i++) {
	                    if (this.elements[i] instanceof StackLayout) {
	                        this.elements[i].setX(this.elements[i].x - shiftBy);
	                    }
	                    else if (this.elements[i] instanceof EmptySpace) {
	                        this.elements[i].x = this.elements[i].x - shiftBy;
	                    }
	                    else {
	                        var tempX = this.elements[i].attr('x');
	                        this.elements[i].attr({ 'x': tempX - shiftBy });
	                    }
	                }
	                posX = prevBBox.x2; +this.gap;
	            }

	            if (this.verticalAlignment == "TOP") {
	                posY = this.y;
	            }
	            else if (this.verticalAlignment == "BOTTOM") {
	                posY = this.y + this.height - element.getBBox().height;
	            }
	            else if (this.verticalAlignment == "MIDDLE") {
	                posY = this.y + (this.height / 2) - (element.getBBox().height / 2);
	            }

	            if (element instanceof StackLayout) {
	                element.setX(posX);
	                element.setY(posY);
	            }
	            else if (element instanceof EmptySpace) {
	                element.x = posX;
	                element.y = posY;
	            }
	            else {
	                element.attr({ 'x': posX, 'y': posY });
	            }
	        }
	        else if (this.stackDirection == 'VERTICAL') {

	            if (this.horizontalAlignment == 'LEFT') {
	                posX = this.x;
	            }
	            else if (this.horizontalAlignment == 'RIGHT') {
	                posX = this.x + this.width - element.getBBox().width;
	            }
	            else if (this.horizontalAlignment == 'CENTER') {
	                posX = (this.x + (this.width / 2)) - (element.getBBox().width / 2);
	            }

	            if (this.verticalAlignment == "TOP") {
	                posY = prevBBox.y2 + this.gap;
	            }
	            else if (this.verticalAlignment == "BOTTOM") {
	                posY = prevBBox.y - this.gap;
	            }
	            else if (this.verticalAlignment == "MIDDLE") {
	                var shiftBy = (element.getBBox().height + this.gap) / 2;
	                for (var i = 0; i < this.elements.length; i++) {
	                    if (this.elements[i] instanceof StackLayout) {
	                        this.elements[i].setY(this.elements[i].Y + shiftBy);
	                    }
	                    else if (this.elements[i] instanceof EmptySpace) {
	                        this.elements[i].y = this.elements[i].y + shiftBy;
	                    }
	                    else {
	                        var tempY = this.elements[i].attr('y');
	                        this.elements[i].attr({ 'y': tempY + shiftBy });
	                    }
	                }
	                posY = prevBBox.y; -this.gap - element.getBBox().height;
	            }

	            if (element instanceof StackLayout) {
	                element.setX(posX);
	                element.setY(posY);
	            }
	            else if (element instanceof EmptySpace) {
	                element.x = posX;
	                element.y = posY;
	            }
	            else {
	                element.attr({ 'x': posX, 'y': posY });
	            }
	        }
	    }

	    this.elements[this.index] = element;
	    this.index = this.index + 1;
	    return element;
	}
	
	
	
}

function EmptySpace(x,y,width,height)
{
	this.width = width;
	this.heigh = height;
	this.x = x;
	this.y = y;
	this.getBBox = function()
	{
		return {x:this.x,y:this.y,x2:this.x+width,y2:this.y+height,width:this.width,height:this.height};
	}
	
}