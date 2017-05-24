# Computational Intelligence Algorithms for Covering Codes

The Computational Intelligence for Covering Codes (CICC) framework is a tool for developers to implement custom computational intelligence (CI) algorithms for the purpose of finding minimal covering codes.

## MOTIVATION
Currently, no public frameworks exist to efficiently find minimal covering codes, thus the primary motivation for the CICC framework is to fill this gap.
At its core, the CICC framework serves to compare all types of computational intelligence algorithms on
minimal covering problems, running comparisons as quick as possible and saving results in an easy-to-read
format. Algorithm implementation aims to be simple, representing a plug-and-play style framework. It is our hope that developers can utilize the CICC framework to avoid writing boilerplate code, instead focussing on implementing creative new computational intelligence algorithms.

## BACKGROUND:
The CICC framework was originally developed by [Justin Maltese](https://ca.linkedin.com/in/justin-maltese-b41a8155) during his graduate studies.
It was originally developed offline without git, hence why the initial commit contains the bulk of the project files.

## FEATURES:

- The CICC framework provides a variety of different features, which include the following:
- Sleek graphical interface for users to select algorithms and minimal covering code problems for comparison
purposes in a step-by-step fashion
- Fast, easy comparison of algorithms on the minimal covering code problem
- Live updates of algorithm performance
- Automatic saving of best-ever-found minimal covering codes. Upon finding a new best-ever code for
a problem, the code is written out as a .code file to the Codes folder.
- Ability to easily implement new CI algorithms along with respective parameter sets in a plug-and-play
fashion
- Developers can setup algorithm comparisons directly in code rather than using a graphical interface
- Ability to automatically write comparison results into nicely-formatted LaTeX tables

## RESOURCES:

- Complete documentation can be found [here](CICCFrameworkUserManual.pdf).
- A sample research paper which utilized the CICC framework for results can be found [here](Report.pdf).

## FUTURE IMPROVEMENTS:

- Saving compatibility matrices as binary files instead of raw text files will greatly reduce .matrix file sizes.
- Adapting the framework to distribute workload among multiple machines will result in incredible speed and scalability

## FAQ:

1. __Q:__ I am running out of memory when attempting to load/generate large compatibility matrices!
   
   __A:__ Allocate more heap space by running java with arguments -Xmx <desired heap size>
2. __Q:__ A runtime error is occurring stating that the compatibility matrix file is missing for the covering
problem I’m trying to run?

   __A:__ This error occurs when the compatibility matrix file has not been generated (or has been misplaced).
The compatibility matrix can be generated using the matrix generator interface.
3. __Q:__ When loading algorithm schemas, the program throws an exception?

   __A:__ This error occurs when their is a formatting problem within the XML schema files. Ensure that the
file is in the correct format, detailed in Section 3.2.

4. __Q:__ When loading my desired algorithm schema, the loaded schema is incorrect!
   
   __A:__ Ensure that the algorithm schema has an id attribute which is unique.
5. __Q:__ When writing results out in LaTeX format, the program is unable to write the file.
  
   __A:__ Ensure that the chosen output path has full write permissions.
6. __Q:__ I want to use this framework to find optimal codes as well!
   
   __A:__ Currently, the framework only supports covering codes. However, it is possible that in a future
release optimal codes will be supported as well. Especially since the modifications required to accomodate
optimal code problems are trivial.
7. __Q:__ Is there any way to turn off the automatic .code file saving for newly found bests?
   
   __A:__ Currently, there is no way to turn off the automatic saving. It is a bad idea to do so, since .code
files can be checked for errors to ensure that the program is not erroneously finding invalid minimal
covering code bounds. This ensures the integrity of research which stems from experiments performed
using the CICC framework.
