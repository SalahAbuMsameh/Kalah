<%@ page language="java" contentType="text/html; charset=windows-1256" pageEncoding="windows-1256"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<html>
	<head>
		<title>Kalah Game</title>
		<link rel="stylesheet" href="${pageContext.request.contextPath}/resources/kalah.css" />
	</head>
	<body>
		<div class="container">
			<div class="header">
				<h1>Welcome to Kalah web game</h1>
			</div>
			<h3>Palyer turn : ${playerTurnName}</h3>
			
			<div class="game-board">
				<div class="player-house">
					<span>${playerTwoBean.house}</span>
				</div>
				
				<table id="pits">
					<tbody>	
						<!-- Player Two -->
						<tr>
							<c:set var="disabled" value="${fn:containsIgnoreCase(playerTurnName, 'two') ? '' : 'disabled'}"/>
							<c:forEach items="${playerTwoBean.pits}" var="pitValue" varStatus="element">
								<td>
									<a class="${disabled}" href="home?move=${element.index}"><span>${pitValue}</span></a>
								</td>
							</c:forEach>
						</tr>
						<!-- Player Two -->
						<tr>
							<c:set var="disabled" value="${fn:containsIgnoreCase(playerTurnName, 'one') ? '' : 'disabled'}"/>
							<c:forEach items="${playerOneBean.pits}" var="pitValue" varStatus="element">
								<td>
									<a class="${disabled}" href="home?move=${element.index}"><span>${pitValue}</span></a>
								</td>
							</c:forEach>
						</tr>
					</tbody>
				</table>
				<div class="player-house">${playerOneBean.house}</div>
			</div>
			<div id="footer">
				<c:choose>
					<c:when test="${playerOneBean.winner}">
						<h2>${playerOneBean.name} Wins</h2>
					</c:when>
					<c:when test="${playerTwoBean.winner}">
						<h2>${playerTwoBean.name} Wins</h2>
					</c:when>
				</c:choose>
			</div>
		</div>
	</body>
</html>