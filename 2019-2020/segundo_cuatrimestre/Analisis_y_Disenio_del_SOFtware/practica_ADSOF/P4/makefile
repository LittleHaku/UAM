

generate_doc:
	@javadoc -d doc -author -sourcepath ./src -subpackages .

compile:
	@rm -rf bin2
	@mkdir bin2
	@find -name "*.java" > class_names.txt
	@javac -d bin2 @class_names.txt

run1:
	@cd bin2; java test.PhysicalUnitTest

run2:
	@cd bin2; java test.MetricSystemTest

run3:
	@cd bin2; java test.MainTest

run4:
	@cd bin2; java test.ConversionTest

run5:
	@cd bin2; java test.CompositeTest


