<%@page pageEncoding="UTF-8" contentType="text/html; charset=UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!doctype html>
<html lang="it" class="h-100">
<head>
	<jsp:include page="../../header.jsp" />
	<title>Pagina dei risultati</title>
	
</head>
<body class="d-flex flex-column h-100">
	<jsp:include page="../../navbar.jsp" />
	
	<!-- Begin page content -->
	<main class="flex-shrink-0">
	  <div class="container">
	
			<div class="alert alert-success alert-dismissible fade show  ${successMessage==null?'d-none':'' }" role="alert">
			  ${successMessage}
			  <button type="button" class="btn-close" data-bs-dismiss="alert" aria-label="Close" ></button>
			</div>
			<div class="alert alert-danger alert-dismissible fade show ${errorMessage==null?'d-none':'' }" role="alert">
			  ${errorMessage}
			  <button type="button"  class="btn-close" data-bs-dismiss="alert" aria-label="Close" ></button>
			</div>
			
			<div class='card'>
			    <div class='card-header'>
			        <h5>Lista dei risultati</h5> 
			    </div>
			    <div class='card-body'>
			    
			        <div class='table-responsive'>
			            <table class='table table-striped ' >
			                <thead>
			                    <tr>
			                        <th>Tipo Permesso</th>
			                        <th>Codice Certificato</th>
			                        <th>Data Inizio</th>
			                        <th>Data Fine</th>
			                        <th>Stato</th>
			                        <th>Azioni</th>
			                    </tr>
			                </thead>
			                <tbody>
			                	<c:forEach items="${richiestepermesso_list_attribute }" var="richiestaItem">
									<tr>
										<td>${richiestaItem.tipoPermesso }</td>
										<td>${richiestaItem.codiceCertificato }</td>
										<td>${richiestaItem.dataInizio }</td>
										<td>${richiestaItem.dataFine }</td>
										<td>${richiestaItem.approvato?'APPROVATO':'NON APPROVATO' }</td>
										<td>
											<a class="btn btn-sm btn-outline-secondary" href="${pageContext.request.contextPath}/richiestapermesso/showRichiestaPermessoBackoffice/${richiestaItem.id }">Visualizza</a>
										
										</td>
									</tr>
								</c:forEach>
			                </tbody>
			            </table>
			        </div>
			        
			        <a href="${pageContext.request.contextPath}/home" class='btn btn-outline-secondary' >
				            <i class='fa fa-chevron-left'></i> Torna alla Home
				        </a>
			   
				<!-- end card-body -->			   
			    </div>
			</div>	
	
		</div>	
	<!-- end container -->	
	</main>
	<jsp:include page="../../footer.jsp" />
	
	
</body>
</html>