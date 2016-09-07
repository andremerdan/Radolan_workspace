var karte;
var karteContext;

var imageX = [];
var imageY = [];
var movedimageX = [];
var movedimageY = [];
var pressedX, pressedY;
var dragging = false;
var image = []
var basemap = new Image(4096, 4096);

var canvasIndex;
var current = [];
var area = [];
var foundEntries = [];
var collection;
var collectionDivision = 10;

var interval;
var formSelector;

var timeline;
var zoom = [];

//Listener
function setEventListeners(){
	image[0] = new Image();
	image[1] = new Image();
	
	window.addEventListener("mouseup", releaseDrag, false);
	window.addEventListener("resize", gainResolution, false);
	document.getElementById("markAsNegativ").addEventListener("click", markAsNegative, false);
	document.getElementById("markAsPotential").addEventListener("click", markAsPotential, false);
	formSelector.addEventListener("change", switchFoundInput, false);
	
	document.getElementById("queryValueButton").addEventListener("click", queryDataValue, false);
	document.getElementById("formMaxVal").addEventListener("change", setMaxVal, false);
	document.getElementById("formEntryName").addEventListener("change", setEntryName, false);
	document.getElementById("createEntryForm").addEventListener("click", createFoundInput, false);
	document.getElementById("entryformSend").addEventListener("click", sendFoundInput, false);
	document.getElementById("deleteformSend").addEventListener("click", deleteFoundInput, false);
	document.getElementById("formRainDelete").addEventListener("click", deleteRainCenter, false);
	document.getElementById("startAnimation").addEventListener("click", beginInterval, false);
	document.getElementById("stopAnimation").addEventListener("click", stopInterval, false);
	
	document.getElementById("drawShownEntry").addEventListener("click", drawKarte, false);
	document.getElementById("drawCurrentEntry").addEventListener("click", drawKarte, false);
	
	document.getElementById("formTimeStartMinus").addEventListener("click", function(){
		shiftFormTimes(0, 0);
	}, false);
	document.getElementById("formTimeStopMinus").addEventListener("click",  function(){
		shiftFormTimes(1, 0);
	}, false);
	document.getElementById("formTimeStartPlus").addEventListener("click",  function(){
		shiftFormTimes(0, 1);
	}, false);
	document.getElementById("formTimeStopPlus").addEventListener("click",  function(){
		shiftFormTimes(1, 1);
	}, false);
	
	image[0].addEventListener("load", drawKarte, false);
	image[1].addEventListener("load", drawKarte, false);
}

function setEntryName(){
	var i = formSelector.selectedIndex;
	formSelector.options[i].text = document.getElementById("formEntryName").value;
	foundEntries[i][6] = document.getElementById("formEntryName").value;
}

function setMaxVal(){
	var i = formSelector.selectedIndex;
	var regex = /^((\d|[1-9]\d+)(\.\d{1,1})?|\.\d{1,1})$/;
	var val = document.getElementById("formMaxVal").value;
	if(!regex.test(val)){
		val = 0;
	}
	document.getElementById("formMaxVal").value = val;
	foundEntries[i][4] = 0;
	foundEntries[i][4] = parseInt(document.getElementById("formMaxVal").value * collectionDivision);
}

function shiftFormTimes(time, direction){
	var i = formSelector.selectedIndex;
	var add = 300000;
	if(collection == "rw"){
		add = 3600000;
	}
	if(direction == 0){
		add = add * -1;
	}
	
	var timeStart = parseInt(foundEntries[i][0]);
	var timeStop = parseInt(foundEntries[i][1]);
	
	if(time == 0){
		timeStart = timeStart + add;
		if(timeStart > timeStop){
			return;
		}
		foundEntries[i][0] = timeStart;
		
		var start = new Date();
		start.setTime(timeStart);
		
		document.getElementById("formTimeStart").innerHTML = start.toString().slice(0, 21);
	} else {
		timeStop = timeStop + add;
		if(timeStart > timeStop){
			return;
		}
		foundEntries[i][1] = timeStop;
		
		var stop = new Date();
		stop.setTime(timeStop);
		
		document.getElementById("formTimeStop").innerHTML = stop.toString().slice(0, 21);
	}
}

function deleteRainCenter(){
	var i = formSelector.selectedIndex;
	foundEntries[i][3] = [];
	drawKarte();
}

function changezoom(event){
	if(event.wheelDelta > 0 || event.detail < 0){
		if(zoom[canvasIndex] > 0.2){
			zoom[canvasIndex] = zoom[canvasIndex] - 0.2;
		}
	} else {
		zoom[canvasIndex] = zoom[canvasIndex] + 0.2;
	}
	event.preventDefault();
	drawKarte();
	return false;
}

function queryDataValue(event){
	var x = document.getElementById("queriedX").value.replace(/[^0-9]+/,"");
	var y = document.getElementById("queriedY").value.replace(/[^0-9]+/,"");
	
	var arr = [];
	$.ajax({
		url: "http://localhost:8080/radolan/data/" + current[canvasIndex][3] + "/" + x + "/" + y
	}).then(function(data) {
		var json = JSON.stringify(eval(data));
		arr = $.parseJSON(json);
		var val = arr / collectionDivision;
		document.getElementById("queriedValue").innerHTML = val;
	});
}

function manipulateImage(event){
	if(dragging == true){
		return;
	}
	if(this.id != current[canvasIndex][0]){
		
		/*document.getElementById("drawShownEntry").checked = false;
		document.getElementById("drawCurrentEntry").checked = false;*/
		karte.style["border"] = "8px solid #000000";
		if(canvasIndex == 1){
			canvasIndex = 0;
		}
		else{
			canvasIndex = 1;
		}
		karte = document.getElementById(this.id);
		karte.style["border"] = "8px solid #FF0000";
		karteContext = karte.getContext("2d");
		timeline.setSelection(current[canvasIndex][6]);
	}
	if(document.getElementById("ValueQuery").checked == true && current[canvasIndex][1] != 0){
		var rect = karte.getBoundingClientRect();
		var x = event.clientX - rect.left - 8;
		var y = event.clientY - rect.top - 8;

		x = x - movedimageX[canvasIndex];
		y = y - movedimageY[canvasIndex];
		x = x / zoom[canvasIndex];
		y = y / zoom[canvasIndex];
		x = parseInt(x);
		y = parseInt(y);
		
		document.getElementById("queriedX").value = x;
		document.getElementById("queriedY").value = y;
		
		var arr = [];
		$.ajax({
			url: "http://localhost:8080/radolan/data/" + current[canvasIndex][3] + "/" + x + "/" + y
		}).then(function(data) {
			var json = JSON.stringify(eval(data));
			arr = $.parseJSON(json);
			var val = arr / collectionDivision;
			document.getElementById("queriedValue").innerHTML = val;
		});
	}
	else if(document.getElementById("move").checked == true){
		dragging = true;
		pressedX = event.pageX;
		pressedY = event.pageY;
		window.addEventListener("mousemove", repositionImage, false);
	}
	else if(document.getElementById("formRainCenter").checked == true){
		var i = formSelector.selectedIndex;
		var rect = karte.getBoundingClientRect();
		var x = event.clientX - rect.left - 8;
		var y = event.clientY - rect.top - 8;
		
		x = x - movedimageX[canvasIndex];
		y = y - movedimageY[canvasIndex];
		x = x / zoom[canvasIndex];
		y = y / zoom[canvasIndex];
		
		foundEntries[i][3][foundEntries[i][3].length] = x;
		foundEntries[i][3][foundEntries[i][3].length] = y;
		drawKarte();
	}
	else if(document.getElementById("formWindDirection").checked == true){
		dragging = true;
		pressedX = event.pageX;
		pressedY = event.pageY;
		window.addEventListener("mousemove", inputWindDirection, false);
	}
}

function inputWindDirection(){
	var i = formSelector.selectedIndex;
	var x = event.pageX;
	var y = event.pageY;
	
	x = x - pressedX;
	y = pressedY - y;
	
	var boundaryX = x;
	var boundaryY = y;
	
	
	if(boundaryX < 0){
		boundaryX = boundaryX * -1;
	}
	if(boundaryY < 0){
		boundaryY = boundaryY * -1;
	}
	
	var circleSize = boundaryX + boundaryY;
	
	x = x / circleSize;
	y = y / circleSize;
	
	var directionDegree = Math.acos(x) * (180 / Math.PI);
	
	if(directionDegree > 90 && y < 0){
		directionDegree = directionDegree + (180 - directionDegree) * 2;
	}
	else if(directionDegree <= 90 && y < 0){
		directionDegree = 270 + (90 - directionDegree);
	}
	directionDegree = parseInt(directionDegree);
	
	foundEntries[i][5] = directionDegree;
	
	document.getElementById("formWindDegrees").innerHTML = directionDegree;
	if(!dragging){
		window.removeEventListener("mousemove", inputWindDirection);
	}
	drawKarte();
}

function repositionImage(event){
	movedimageX[canvasIndex] = imageX[canvasIndex] + event.pageX - pressedX;
	movedimageY[canvasIndex] = imageY[canvasIndex] + event.pageY - pressedY;

	if(!dragging){
		window.removeEventListener("mousemove", repositionImage);
		imageX[canvasIndex] = movedimageX[canvasIndex];
		imageY[canvasIndex] = movedimageY[canvasIndex];
	}
	drawKarte();
}

function releaseDrag(){
	dragging = false;
}

function getCanvasPosition(event, canvas, calledFunction){
	var relativeX = event.pageX - canvas.offsetLeft; 
	var relativeY = event.pageY - canvas.offsetTop;

	calledFunction(relativeX, relativeY);
}

function changePageSize(x, y){
	var size = x - 32;
	size = size / 2;
	document.getElementById(current[0][0]).width = size * 0.9;
	document.getElementById(current[0][0]).height = size * 0.9;
	document.getElementById(current[1][0]).width = size * 0.9;
	document.getElementById(current[1][0]).height = size * 0.9;
}

function gainResolution(){
	var x = window.innerWidth;
	var y;
	if(x == null){
		x = document.body.clientWidth;
		y = document.body.clientHeight;
	}
	y = window.innerHeight;
	changePageSize(x, y);
}

function drawKarte(){
	karteContext.fillStyle = "#ffffff";
	karteContext.fillRect(0, 0, karte.width, karte.height);
	karteContext.fillStyle = "#000000";
	
	karteContext.translate(movedimageX[canvasIndex], movedimageY[canvasIndex]);
	karteContext.drawImage(basemap, 0, 0, 900 * zoom[canvasIndex], 900 * zoom[canvasIndex]);
	karteContext.drawImage(image[canvasIndex], area[0] * zoom[canvasIndex], area[1] * zoom[canvasIndex], (image[canvasIndex].width * zoom[canvasIndex]), (image[canvasIndex].height * zoom[canvasIndex]));
	
	if(document.getElementById("drawShownEntry").checked == true  && current[canvasIndex][4] == 1){
		
		karteContext.lineWidth = 2;
		var i = current[canvasIndex][5];
		var centers = foundEntries[i][3];
		var direction = foundEntries[i][5];
		
		var windX = Math.cos(direction * (Math.PI / 180));
		var windY = Math.sin(direction * (Math.PI / 180));
		
		karteContext.beginPath();
		karteContext.moveTo(centers[0] * zoom[canvasIndex], centers[1] * zoom[canvasIndex]);
		for(h = 2; h < centers.length; h = h + 2){
			karteContext.lineTo(centers[h] * zoom[canvasIndex], centers[h + 1] * zoom[canvasIndex]);
		}
		karteContext.stroke();
		karteContext.beginPath();
		karteContext.lineWidth = 4;
		karteContext.strokeStyle = "#ff0000";
		karteContext.moveTo(centers[0] * zoom[canvasIndex], centers[1] * zoom[canvasIndex]);
		karteContext.lineTo(centers[0] * zoom[canvasIndex] + 15 * windX * zoom[canvasIndex], centers[1] * zoom[canvasIndex] - 15 * windY * zoom[canvasIndex]);
		karteContext.stroke();
		karteContext.strokeStyle = "#000000";
	}
	if(document.getElementById("drawCurrentEntry").checked == true){
		karteContext.lineWidth = 2;
		var i = formSelector.selectedIndex;
		var centers = foundEntries[i][3];
		var direction = foundEntries[i][5];
		
		var windX = Math.cos(direction * (Math.PI / 180));
		var windY = Math.sin(direction * (Math.PI / 180));
		
		karteContext.beginPath();
		karteContext.moveTo(centers[0] * zoom[canvasIndex],centers[1] * zoom[canvasIndex]);
		for(h = 2; h < centers.length; h = h + 2){
			karteContext.lineTo(centers[h] * zoom[canvasIndex], centers[h + 1] * zoom[canvasIndex]);
		}
		karteContext.stroke();
		karteContext.beginPath();
		karteContext.lineWidth = 4;
		karteContext.strokeStyle = "#ff0000";
		karteContext.moveTo(centers[0] * zoom[canvasIndex], centers[1] * zoom[canvasIndex]);
		karteContext.lineTo(centers[0] * zoom[canvasIndex] + 15 * windX * zoom[canvasIndex], centers[1] * zoom[canvasIndex] - 15 * windY * zoom[canvasIndex]);
		karteContext.stroke();
		karteContext.strokeStyle = "#000000";
	}
	karteContext.lineWidth = 1;
	
	karteContext.fillStyle = "#ffffff";
	karteContext.fillRect(0, 0, area[0] * zoom[canvasIndex], karte.height - movedimageY[canvasIndex]);
	karteContext.fillRect(0, 0, karte.width - movedimageX[canvasIndex], area[1] * zoom[canvasIndex]);
	karteContext.fillRect(area[2] * zoom[canvasIndex], 0, karte.width - movedimageX[canvasIndex], karte.height - movedimageY[canvasIndex]);
	karteContext.fillRect(0, area[3] * zoom[canvasIndex], karte.width - movedimageX[canvasIndex], karte.height - movedimageY[canvasIndex]);
	karteContext.fillStyle = "#000000";
	karteContext.translate(movedimageX[canvasIndex] * -1, movedimageY[canvasIndex] * -1);
}

function onIntervalPassed(){
	if(current[canvasIndex][1] == 0){
		stopInterval();
		return;
	}
	if(collection == "rw"){
		current[canvasIndex][3] = current[canvasIndex][3] + 3600000;
	}
	else {
		current[canvasIndex][3] = current[canvasIndex][3] + 300000;
	}
	
	if(current[canvasIndex][3] > current[canvasIndex][2]){
		current[canvasIndex][3] = current[canvasIndex][1].getTime();
	}
	var path = buildImagePath(current[canvasIndex][3]);
	image[canvasIndex].src = "dataImages/" + path;
}

function beginInterval(){
	if(interval == null){
		interval = window.setInterval(onIntervalPassed, 1500);
	}
}

function stopInterval(){
	if(interval != null){
		window.clearInterval(interval);
	}
	interval = null;
}

function onselect(properties) {
	var sel = timeline.getSelection();
	if (sel.length) {
		if (sel[0].row != undefined) {
			var row = sel[0].row;
			current[canvasIndex][1] = timeline.getItem(row).start;
			current[canvasIndex][2] = timeline.getItem(row).endDate;
			current[canvasIndex][3] = timeline.getItem(row).start.getTime();
			current[canvasIndex][4] = timeline.getItem(row).entryType;
			if(current[canvasIndex][4] == 1){
				current[canvasIndex][5] = timeline.getItem(row).arrayPlace;
				formSelector.selectedIndex = current[canvasIndex][5];
				switchFoundInput();
			}
			current[canvasIndex][6] = sel;
			var path = buildImagePath(timeline.getItem(row).start.getTime());
			image[canvasIndex].src = "dataImages/" + path;
		}
	}
}

function buildImagePath(time){
	return time + '.png';
}

function createFoundInput(){
	if(current[canvasIndex][1] == 0){
		return;
	}
	var i = foundEntries.length;
	foundEntries[i] = [];
	foundEntries[i][0] = parseInt(current[canvasIndex][1].getTime());
	foundEntries[i][1] = parseInt(current[canvasIndex][2].getTime());
	foundEntries[i][2] = "undefined";
	foundEntries[i][3] = [];
	foundEntries[i][4] = 0;
	foundEntries[i][5] = 0;
	foundEntries[i][6] = "New Entry " + i;
	
	createEntry(i, "New Entry " + i);
	formSelector.selectedIndex = i;
	switchFoundInput();
}

function createEntry(length, name){
	var option = document.createElement('option');
	option.text = name;
	formSelector.appendChild(option);
}

function switchFoundInput(){
	var i = formSelector.selectedIndex;
	
	var start = new Date();
	var stop = new Date();
	start.setTime(foundEntries[i][0]);
	stop.setTime(foundEntries[i][1]);
	
	document.getElementById("formTimeStart").innerHTML = start.toString().slice(0, 21);
	document.getElementById("formTimeStop").innerHTML = stop.toString().slice(0, 21);
	document.getElementById("formMaxVal").value = foundEntries[i][4] / collectionDivision;
	document.getElementById("formWindDegrees").innerHTML = foundEntries[i][5];
	document.getElementById("formEntryName").value = foundEntries[i][6] + "";
	drawKarte();
}

function sendFoundInput(){
	var i = formSelector.selectedIndex;
	var arr = [];
	
	arr[0] = foundEntries[i][0] + "";
	arr[1] = foundEntries[i][1] + "";
	arr[2] = foundEntries[i][4] + "";
	arr[3] = foundEntries[i][5] + "";
	arr[4] = foundEntries[i][6] + "";
	var jsonStrings = JSON.stringify(arr);
	var centersArr = [];
	var n = foundEntries[i][3].length;
	for(x = 0; x < n; x++){
		centersArr[x] = parseInt(foundEntries[i][3][x]);
	}
	var jsonCenters = JSON.stringify(centersArr);
	$.ajax({
		url: "http://localhost:8080/radolan/data/entry/" + jsonStrings + "/" + jsonCenters + "/" + foundEntries[i][2]
	}).then(function(data) {
		/*alert(data);
		var incoming;
		var json = JSON.stringify(eval(data));
		incoming = $.parseJSON(json);
		foundEntries[i][2] = incoming;
		$.ajax({
			url: "http://localhost:8080/radolan/data/glyph/" + foundEntries[i][2]
		}).then(function(data) {
			var foundInTimeline = 0;
			var data = timeline.getData();
			var n = data.length;
			for(x = 0; x < n; x++){
				if(data[x].entryType == 1){
					if(data[x].arrayPlace == i){
						data[x].content = '<img src="dataImages/' + foundEntries[i][2] + '" style="width:65px; height:65px;">';
						foundInTimeline = 1;
					}
				}
			}
			if(foundInTimeline == 0){
				startTime = new Date();
				endTime = new Date();
				startTime.setTime(foundEntries[i][0]);
				endTime.setTime(foundEntries[i][1]);
				data[n].start = startTime;
				data[n].end = endTime;
				data[n].entryType = 1;
				data[n].arrayPlace = i;
				data[n].content = '<img src="dataImages/' + foundEntries[i][2] + '" style="width:65px; height:65px;">';
			}
			
			timeline.setData(data);
			timeline.redraw();
			alert("Found in Timeline " + foundInTimeline);
			alert("Redrawn with " + foundEntries[i][2]);
		});*/
	});
}

function deleteFoundInput(){
	var i = formSelector.selectedIndex
	document.getElementById("formTimeStart").innerHTML = "";
	document.getElementById("formTimeStop").innerHTML = "";
	document.getElementById("formMaxVal").value = 0;
	document.getElementById("formWindDegrees").innerHTML = "";
	document.getElementById("formEntryName").value = "";
	
	$.ajax({
		url: "http://localhost:8080/radolan/data/delete/" + foundEntries[i][2]
	}).then(function(data) {
		/*foundEntries.splice(i, 1);
		formSelector.options.splice(i, 1);
		formSelector.selectedIndex = 0;
		switchFoundInput();
		var data = timeline.getData();
		var n = data.length;
		var foundSel = data.length;
		for(x = 0; x < n; x++){
			if(data[x].entryType == 1){
				if(data[x].arrayPlace == i){
					data.splice(x, 1);
					foundSel = x;
					alert("Found Deletion at "  + x);
					break;
				}
			}
		}
		for(x = foundSel; x < n - 1; x++){
			alert("Adapting " + x);
			if(data[x].entryType == 1){
				data[x].arrayPlace = data[x].arrayPlace - 1;
			}
		}
		timeline.setData(data);
		timeline.redraw();*/
	});
}

function markAsNegative(){
	var arr = [];
	if(current[canvasIndex][1] == 0){
		return;
	}
	arr[0] = current[canvasIndex][1].getTime() + "";
	arr[1] = current[canvasIndex][2].getTime() + "";
	
	var jsonStrings = JSON.stringify(arr);
	var jsonArea = JSON.stringify(area);
	$.ajax({
		url: "http://localhost:8080/radolan/data/negative/" + jsonStrings + "/" + jsonArea
	}).then(function(data) {
		
	});
}

function markAsPotential(){
	var arr = [];
	if(current[canvasIndex][1] == 0){
		return;
	}
	arr[0] = current[canvasIndex][1].getTime() + "";
	arr[1] = current[canvasIndex][2].getTime() + "";
	
	var jsonStrings = JSON.stringify(arr);
	var jsonArea = JSON.stringify(area);
	$.ajax({
		url: "http://localhost:8080/radolan/data/positive/" + jsonStrings + "/" + jsonArea
	}).then(function(data) {
		
	});
}

$(document).ready(function() {
	basemap.src = "img/RadolanBearbeitet.png";
	var arr = [];
	var start;
	var end;
	$.ajax({
		url: "http://localhost:8080/radolan/data/time"
	}).then(function(data) {
		var json = JSON.stringify(eval(data));
		arr = $.parseJSON(json);
		
		collection = arr[0];
		if(collection == "ry"){
			collectionDivision = 100;
		}
		area[0] = parseInt(arr[1]);
		area[1] = parseInt(arr[2]);
		area[2] = parseInt(arr[3]);
		area[3] = parseInt(arr[4]);
		var items = [];
		
		for (i = 5; i < arr.length; i = i + 2) {
			start = new Date();
			end = new Date();
			start.setTime(parseInt(arr[i]));
			end.setTime(parseInt(arr[i + 1]));
			items.push({
				'start': start,
				'endDate' : end,
				'entryType' : 0,
				'content': 'Von ' + start.toString().slice(0, 21) + ' bis ' + end.toString().slice(0, 21) + ' ?'
			});
		}
		
		$.ajax({
			url: "http://localhost:8080/radolan/data/found"
		}).then(function(data) {
			json = JSON.stringify(eval(data));
			foundEntries = $.parseJSON(json);
			
			for (i = 0; i < foundEntries.length; i++) {
				var obj = foundEntries[i];
				foundEntries[i][0] = obj.t1;
				foundEntries[i][1] = obj.t2;
				foundEntries[i][2] = obj.g;
				foundEntries[i][3] = obj.c;
				foundEntries[i][4] = obj.mv;
				foundEntries[i][5] = obj.w;
				foundEntries[i][6] = obj.b;
				createEntry(i, obj.b);
				
				start = new Date();
				end = new Date();
				start.setTime(foundEntries[i][0]);
				end.setTime(foundEntries[i][1]);
				items.push({
					'start': start,
					'endDate' : end,
					'entryType' : 1,
					'arrayPlace' : i,
					'content': '<img src="dataImages/' + foundEntries[i][2] + '" style="width:65px; height:65px;">'
				});
			}
			var options = {
				"width":  "100%",
				"height": "auto",
				"style": "box",
				"editable": false
			};

			timeline = new links.Timeline(document.getElementById('visualization'));

			timeline.draw(items, options);

			links.events.addListener(timeline, 'select', onselect);
			if(foundEntries.length >= 1){
				formSelector.selectedIndex = 0;
				switchFoundInput();
			}
		});

	});
	
	var canvas0 = document.createElement("canvas");
	var canvas1 = document.createElement("canvas");
	canvas0.id = "canvas0";
	canvas1.id = "canvas1";
	current[0] = [];
	current[0][0] = canvas0.id;
	current[0][1] = 0;	
	current[1] = [];
	current[1][0] = canvas1.id;
	current[1][1] = 0;
	canvasIndex = 0;
	canvas0.style["border"]="8px solid #FF0000";
	canvas1.style["border"]="8px solid #000000";
	karte = canvas0;
	canvas0.addEventListener("mousedown", manipulateImage, false);
	canvas0.addEventListener("mousewheel", changezoom, false);
	canvas1.addEventListener("mousedown", manipulateImage, false);
	canvas1.addEventListener("mousewheel", changezoom, false);
	document.getElementById('karten').appendChild(canvas0);
	document.getElementById('karten').appendChild(canvas1);
	karteContext = karte.getContext("2d");
	
	imageX[0] = 0;
	imageX[1] = 0;
	imageY[0] = 0;
	imageY[1] = 0;
	
	movedimageX[0] = 0;
	movedimageX[1] = 0;
	movedimageY[0] = 0;
	movedimageY[1] = 0;
	
	zoom[0] = 1;
	zoom[1] = 1;
	
	gainResolution();
	formSelector = document.getElementById("entryFormSelect");
	
	setEventListeners();
});