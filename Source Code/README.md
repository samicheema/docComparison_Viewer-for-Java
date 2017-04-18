# docComparison_Viewer-for-Java #

docComparison_Viewer-for-Java is a MVC Web Application that demonstrates the core functionalities provided by GroupDocs.Comparison/Viewer for JAVA. The UI of the application is kept very simple and developers can enhance the features as their own needs since it is an Open Source Project.

## Features ##

* Compare two documents
* View compared document
* Download compared document

## How it Works ##

#### Index.jsp #### 
Is the file present under “WEB-INF” folder in “src/main/webapp” folder which contains the html. It gets two documents compare them and shows the compared document.

#### FileUploadController.java #### 
Is the controller file present under “Controllers” folder “src/main/java/com/docs/controller” which contains code implementation of uploading two documents, verification of their extensions and make them ready for comparison.

#### ComparisonGenerator.java #### 
Is the class present under “src/main/java/com/docs/comparison” folder which contains the code implementation of comparing two documents using GroupDocs.Comparison API.

#### ViewGenerator.java #### 
Is the class present under “src/main/java/com/docs/viewer” folder which contains the code implementation of converting compared document into image using GroupDocs.Viewer API.
