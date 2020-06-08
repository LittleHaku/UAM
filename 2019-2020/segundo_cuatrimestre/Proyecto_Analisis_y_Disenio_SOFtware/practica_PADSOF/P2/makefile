MAKEFLAGS += --jobs 5
DIAGRAMS=$(foreach F,$(wildcard *.plantuml),Report/Images/svg/$(subst .plantuml,.svg ,$F))

NAMES=$(foreach F,$(wildcard *.plantuml), $(subst .plantuml, ,$F))

print= @printf "Creado: \033[1m\033[1;34m $(1) \033[0;30m\033[0m\t\n"
 
all: $(DIAGRAMS)
	@cd Report/; latexmk  -shell-escape -pdf -silent report.tex

clean:
	rm -f $(DIAGRAMS)
	rm -f $(foreach F,$(NAMES), Report/Images/$F.pdf)
	rm -f Report/report.pdf


$(DIAGRAMS): Report/Images/svg/%.svg:%.plantuml
	@java -jar plantuml.jar -tsvg -o Report/Images/svg/ -filename $(subst Report/Images/svg/,,$@) $(subst .svg,.plantuml,$(subst Report/Images/svg/,,$@))
	# @inkscape $@ --export-pdf=Report/Images/$(subst .svg,.pdf,$(subst Report/Images/svg/,,$@)) 2>/dev/null
	@rsvg-convert -f pdf $@ -o Report/Images/$(subst .svg,.pdf,$(subst Report/Images/svg/,,$@))
	$(call print,$(subst .svg,,$(subst Report/Images/svg/,,$@)))

onlylaststage:
	@for el in $(NAMES); do \
		echo "Done: "$${el}; \
		rsvg-convert -f pdf  report/Images/svg/$${el}.svg -o report/Images/$${el}.pdf 2>/dev/null; \
	done
	
latex:
	@cd Report/; latexmk  -shell-escape -pdf -silent report.tex