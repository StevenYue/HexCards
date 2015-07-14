//important vars, don't know too much about scope
//in JS at this point, 6/11/2015.
//so figured out better to do this by trial and error
var currentGameType = 0;  // 0,1,2,3
var selectCardsNum = 0;
var selectCards = [];
var cardVoidFourToSix = [0,0,0];
var initPosFourToSix = [0,0,0];
var cardHolderOneToSix = [0,0,0,0,0,0]; 
var yourplunder = 0;
var herplunder = 0;
var yourID;
var msgWindowString;
var gameStarted = false;
var gameInProgress = false;
var handsubmitted = false;
var opponentID = "";
var gameID = "";
var roomID = 0;


$(document).ready(function(){
	//$( "#userList" ).selectmenu();
	
	//get ID from the URL parameter
	yourID = getUrlParameter("ID");
	//WTF once having .text(), html stopped rendering
	$("#yourID").text("Welcome:" + yourID);
	
	msgWindowString = "Welcome:" + yourID + "\n";
	$("#msgWindow").text(msgWindowString);
	
	//bind a "beforeunload" event, tell server if client closed page
	$(window).on('beforeunload', function(){ 
		sendLogOffRequest();
		return "";
		}
	);
	
	
	//bind click listener to clickable whatever
	$("#startButton").bind("click", startButtonClick);
	$("#vs").bind("click", vsClick);
	$(".cardHolder").bind("click", cardHolderClick);
	$(".cardBack").bind("click", cardBackClick);
	$(".cardVoid").bind("click",cardVoidClick);
	
	
	//Select the opponent
	$("#roomList li").bind("click", function(){
		roomListClickedHander($(this));
	});
	
	/*
	setBackgroundPics("yourcard1", "url(\"img/1h.png\")");
	setBackgroundPics("yourcard2", "url(\"img/12h.png\")");
	setBackgroundPics("yourcard3", "url(\"img/99j.png\")");
	*/
});


//Event Handlers definitions here
function startButtonClick(){
	if(gameStarted) return;
	if(opponentID == "") {
		alert("Can't Start alone!!");
		return;
	}
//	gameStarted = true;
	//1.Replace method won't allow return
//	window.location.replace("http://stackoverflow.com");


	//2.This can still return to the original page
	//window.location.href = "http://stackoverflow.com";
	
	//$("#yourID").html("<span id=\"yourID\">yourID:<span>");
	var jMsg = {"GAMEID": gameID};// example:{"firstName":"John", "lastName":"Doe"}
	$.ajax({
		type : 'POST',// GET Or POST
		url  : "/SixCards/gamestartrequest",
		data :jMsg,
		dataType: "json",
	});
	updateMsgWindow("***Ready to Start!***");
	window.setInterval(requestGameDate, 5000);
	herplunder = 0;
	$("#herstats").text(herplunder);
	yourplunder = 0;
	$("#yourstats").text(yourplunder);
}

function vsClick(){
	if(!gameStarted) return;
	if(!gameInProgress) return;
	if(currentGameType != selectCardsNum || currentGameType == 0) return;
	if(handsubmitted) return;
	
	var handinfo = "";
	for(var i=0;i<selectCards.length;i++)
		handinfo = handinfo + selectCards[i] + "-";
	
	var jMsg = {"ID":yourID,"GAMEID":gameID,"HANDINFO": handinfo};
	
	$.ajax({
		type: "POST",
		url : "/SixCards/handsubmitted",
		data: jMsg,
		dataType: "json",
		success: function(response, textStatus, jqHXR){
			var data = response.gamedata;
			if(data.indexOf("died") >= 0){
				updateMsgWindow("***Rules Violation!***");
				updateMsgWindow("***Choose Another!***");
				
			}
		},
		error: function(jqXHR, textStatus, errorThrown){
			handsubmitted = true;
			updateMsgWindow("***Hand Submitted!***");
		}
		
	});
}

function cardHolderClick(){
	if(!gameStarted) return;
	if(selectCardsNum == currentGameType) return;
	var pos;
	if(this.id.indexOf(1)>=0) pos = 1; 
	if(this.id.indexOf(2)>=0) pos = 2; 
	if(this.id.indexOf(3)>=0) pos = 3; 
	if(this.id.indexOf(4)>=0) pos = 4; 
	if(this.id.indexOf(5)>=0) pos = 5; 
	if(this.id.indexOf(6)>=0) pos = 6;
	if(cardHolderOneToSix[pos-1] == 0) return;
	cardHolderOneToSix[pos-1] = 0;
	selectCardsNum++;
	var url = $(this).css("background-image");
//	url = url.replace("url(","").replace(")","");
	var cardInfo = interpretURL(url);
	selectCards.push(cardInfo);
	
	setBackgroundPics(this.id, "");
	if(cardVoidFourToSix[0] == 0){
		setBackgroundPics("cv4",url);
		cardVoidFourToSix[0] = 1;
		initPosFourToSix[0] = pos;
		addEffectToCardVoid("cv4");
	}else if(cardVoidFourToSix[1] == 0){
		setBackgroundPics("cv5",url);
		cardVoidFourToSix[1] = 1;
		initPosFourToSix[1] = pos;
		addEffectToCardVoid("cv5");
	}else if(cardVoidFourToSix[2] == 0){
		setBackgroundPics("cv6",url);
		cardVoidFourToSix[2] = 1;
		initPosFourToSix[2] = pos;
		addEffectToCardVoid("cv6");
	}
}

function cardBackClick(){
	if(!gameStarted) return;
	if(gameInProgress) return;
	var jMsg = {"GAMEID": gameID, "ID": yourID};
	$.ajax({
		type : 'POST',// GET Or POST
		url  : "/SixCards/cardsrequested",
		data :jMsg,
		dataType: "json",
		 
		success: function(response, textStatus, jqHXR){
				var data = response.gamedata;
				for(var i=0;i<data.length;i++){
					var num = i + 1;
					var ref = "yourcard" + num;
					var rank = data[i].match(/\d+/);
					var suit = data[i].charAt(data[i].length - 1);
					setBackgroundImgWithCardInfo(ref, rank, suit);
					cardHolderOneToSix[i] = 1;
				}
				var deadRound = response.deadround;
				if(deadRound == "rounddied"){
					currentGameType = 0;
					updateMsgWindow("***DEAD!!!***");
				}else{
					currentGameType = 1;
					updateMsgWindow("GameType: Single!");
				}
				gameInProgress = true;
			},
		  
		error: function(jqXHR, textStatus, errorThrown){
		    console.log(
		        "The following error occurred at requestCards: "+
		        textStatus, errorThrown
		    );
		}
	});
}

function cardVoidClick(){
	if(!gameStarted) return;
	if(selectCardsNum == 0) return;
	if(handsubmitted) return;
	var index;
	if(this.id.indexOf(4)>=0) index = 0; 
	if(this.id.indexOf(5)>=0) index = 1; 
	if(this.id.indexOf(6)>=0) index = 2; 
	if(cardVoidFourToSix[index] == 0) return;
	cardVoidFourToSix[index] = 0;
	var id = "cv" + (index+4);
	removerEffectFromCardVoid(id);
	var url = $(this).css("background-image");
	var cardInfo = interpretURL(url);
	
	//delete from my selectedCards list
	var i = selectCards.indexOf(cardInfo);
	if(i > -1)
		selectCards.splice(i,1);
	
	selectCardsNum--;
	setBackgroundPics(this.id, "");
	var targetID = "yourcard" + initPosFourToSix[index];
	cardHolderOneToSix[initPosFourToSix[index]-1] = 1;
	setBackgroundPics(targetID, url);
}

//The following two line can be used to do constantly data polling
window.setInterval(requestUserListUpdate, 5000);
window.setInterval(requestRoomListUpdate, 5000);

/*
All ajax requests 
*/
function requestRoomListUpdate() {
	$.ajax({
		type : 'POST',// GET Or POST
		url  : "/SixCards/broadcastroomlist",
		dataType: "json",
		 
		success: function(response, textStatus, jqHXR){
				var data = response.roomlistbroadcast;
				$("#roomList").empty();
				for(var i=0;i<data.length;i++){
					var n = i+1;
					$("#roomList").append("<li>Room "+n+"  "+data[i]+"</li>"+"\n");
					if(data[i].indexOf(yourID) >= 0){
						var ss = data[i].split("-");
						for(p=0;p<ss.length;p++){
							if(ss[p]!=yourID){
								opponentID = ss[p];
								if(yourID>opponentID)
									gameID = yourID+"-"+opponentID;
								else
									gameID = opponentID+"-"+yourID;
							}
						}
					}
				}

				$("#roomList li").unbind("click");
				$("#roomList li").bind("click", function(){
					roomListClickedHander($(this));
				});
	
		},
		  
		error: function(jqXHR, textStatus, errorThrown){
		    console.log(
		        "The following error occurred at requestRoomListUpdate: "+
		        textStatus, errorThrown
		    );
		}
	});
}

function requestUserListUpdate() {
	//alert('1 sec pause');
	$.ajax({
		type : 'POST',// GET Or POST
		url  : "/SixCards/broadcastuserset",
		dataType: "json",
		 
		success: function(response, textStatus, jqHXR){
				var data = response.newuserbroadcast;
				var userIds = data.split("-");
				$("#userList").empty();
				for (var i = 0; i < userIds.length-1; i++){
					if(userIds[i].indexOf(yourID) >= 0) continue;
					
					$("#userList").append("<li>"+userIds[i]+"</li>"+"\n");
				}
/*
				$("#userList li").unbind("click");
				$("#userList li").bind("click", function(){
					userListClickedHander($(this));
				});
*/	
		},
		  
		error: function(jqXHR, textStatus, errorThrown){
		    console.log(
		        "The following error occurred at requestUserListUpdate: "+
		        textStatus, errorThrown
		    );
		}
	});
}

function requestGameDate(){
		var jMsg = {"GAMEID": gameID, "ID": yourID};
		$.ajax({
		type : 'POST',// GET Or POST
		url  : "/SixCards/gamedaterequest",
		data: jMsg,
		dataType: "json",
		success: function(response, textStatus, jqHXR){
				var data = response.gamedata;
				console.log(data);
				if(gameStarted){
					if(data.indexOf("result") > 0){
						var info = data.split("-");
						var winner = info[info.length-1];
						if(winner == yourID){
							updateMsgWindow("Result:You Win!");
							yourplunder += currentGameType*2;
							$("#yourstats").text(yourplunder);
						}else if(winner == opponentID){
							updateMsgWindow("Result:You Lost!");
							herplunder += currentGameType*2;
							$("#herstats").text(herplunder);
						}else{
							updateMsgWindow("Result:Tie!");
							yourplunder += currentGameType;
							$("#yourstats").text(yourplunder);
							herplunder += currentGameType;
							$("#herstats").text(herplunder);
						}
						var handInfo = response.handinfo;
						for(var i=0;i<handInfo.length;i++){
							var num = i + 1;
							var ref = "cv" + num;
							var rank = handInfo[i].match(/\d+/);
							var suit = handInfo[i].charAt(handInfo[i].length - 1);
							setBackgroundImgWithCardInfo(ref, rank, suit);
						}
						setTimeout(clearTheMess, 3000,false);
						
					}else if(data.indexOf("died") >= 0){
						var info = data.split("-");
						if(info.length == 3){//Both dead
							herplunder += 6;
							$("#herstats").text(herplunder);
							yourplunder += 6;
							$("#yourstats").text(yourplunder);
							updateMsgWindow("***Both Died!***");
						}else{
							if(info[1] == yourID){//you died
								herplunder += 12;
								$("#herstats").text(herplunder);
								updateMsgWindow("***You Died!***");
							} else{
								yourplunder += 12;
								$("#yourstats").text(yourplunder);
								updateMsgWindow("***"+opponentID + " Died!***");
							}
						}
						var handInfo = response.handinfo;
						for(var i=0;i<handInfo.length;i++){
							var num = i + 1;
							var ref = "cv" + num;
							var rank = handInfo[i].match(/\d+/);
							var suit = handInfo[i].charAt(handInfo[i].length - 1);
							setBackgroundImgWithCardInfo(ref, rank, suit);
						}
						currentGameType = 3;
						gameInProgress = false;
						setTimeout(clearTheMess, 8000,true);
					}
					
					//If this is the end of the game
					if(response.gameend.indexOf("end") >= 0){
						updateMsgWindow("***Game Finished***");
						gameStarted = false;
						opponentID = "";
						gameID = "";
						if(yourplunder > herplunder){
							updateMsgWindow("***Congs!!!***");
						}else if(yourplunder < herplunder){
							updateMsgWindow("***Loser!!!***");
						}else{
							updateMsgWindow("***Tie????***");
						}
						updateMsgWindow("\n\n\n***Start Another One***\n");
					}
				}else{
					if(data == "gamestart"){ // gameStart Message
						updateMsgWindow("\n***Game Started!***\n");
						$("#herID").text(opponentID+":");
						msg = "Opponent: " + opponentID;
						gameStarted = true;
					}
				}
				
			},
		
		error: function(jqXHR, textStatus, errorThrown){
		    /*console.log(
		        "The following error occurred at requestGameDate: "+
		        textStatus, errorThrown
		    );*/
		}
		});
}


function sendLogOffRequest(){
	var jMsg = {"ID": yourID, "ROOMID":roomID};
	$.ajax({
		type : 'POST',// GET Or POST
		url  : "/SixCards/logoff",
		data: jMsg,
		dataType: "json",
	});
}

/*
All ajax requests ended
*/



//Utility sub-routines
function setBackgroundPics(id, url){
	var ff = "#"+id;
	$(ff).css("background-image", url); 
	$(ff).css("background-size", "120px 168px"); 
}
function setBackgroundImgWithCardInfo(id, rank, suit){
	"url(img/1h.png)"
	var url = "url(img\/" + rank + suit + ".png)";
	var ff = "#" + id;
	$(ff).css("background-image", url); 
	$(ff).css("background-size", "120px 168px"); 
}

function addEffectToCardVoid(id){
	var ff = "#" + id;
	$(ff).css("border","1px solid black");
	$(ff).bind("mouseover",function(){
		$(ff).css("border", "3px solid red");
		$(ff).css("cursor", "pointer");
	}).bind("mouseout",function(){
		$(ff).css("border","1px solid black");
	});
}

function removerEffectFromCardVoid(id){
	var ff = "#" + id;
	$(ff).css("border","0px");
	$(ff).off("mouseover");
	$(ff).off("mouseout");
}

function updateMsgWindow(msg){
	msgWindowString = msg +"\n" + msgWindowString;
	$("#msgWindow").text(msgWindowString);
}

function roomListClickedHander(item){
	if(gameStarted) return;
	var str = $(item).text();
	roomID = str.charAt(5);
	
	var jMsg = {"ROOMID": roomID, "ID":yourID};
	$.ajax({
		type : 'POST',// GET Or POST
		url  : "/SixCards/enterroom",
		data: jMsg,
		dataType: "json",
	});
}

function userListClickedHander(item){
	if(gameStarted) return;
	if($(item).text().indexOf("In Game")>=0) return;
	opponentID = $(item).text();
	if(yourID>opponentID)
		gameID = yourID+"-"+opponentID;
	else
		gameID = opponentID+"-"+yourID;
	$("#herID").text(opponentID+":");
	msg = "Opponent: " + opponentID;
 	updateMsgWindow(msg);
}

function interpretURL(url){
	var len = url.length;
	var temp = url.substring(len - 8);
	temp = temp.substring(0,3);
	if(temp[0] == "\/")
		temp = temp.substring(1);
	return temp;
}


function clearTheMess(isdied){
	for(var i=1;i<7;i++){
		var id = "cv" + i;
		setBackgroundPics(id, "");
		removerEffectFromCardVoid(id);
		if(isdied){
			var chid = "yourcard" + i;
			setBackgroundPics(chid, "");
		}
	}
	for(i=0;i<3;i++){
		initPosFourToSix[i] = 0;
		cardVoidFourToSix[i] = 0;
	}
		
	
	handsubmitted = false;
	selectCardsNum = 0;
	selectCards = [];// clear out this selectedCard array
	currentGameType++;
	if(currentGameType == 1){
		updateMsgWindow("GameType: Single!");
	}else if(currentGameType == 2){
		updateMsgWindow("GameType: TenHalf!");
	}else if(currentGameType == 3){
		updateMsgWindow("GameType: Tractor!");
	}
	else if(currentGameType == 4){
		gameInProgress = false;
		currentGameType = 0;
		if(gameStarted)
			updateMsgWindow("\n***Another Round!***\n");
	}
}

//Copy from stackOverflow.com, get para from url
function getUrlParameter(sParam){
    var sPageURL = window.location.search.substring(1);
    var sURLVariables = sPageURL.split('&');
    for (var i = 0; i < sURLVariables.length; i++) 
    {
        var sParameterName = sURLVariables[i].split('=');
        if (sParameterName[0] == sParam) 
        {
            return sParameterName[1];
        }
    }
}     



