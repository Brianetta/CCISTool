# CCISTool
## Free CCIS XML validation tool
This application is a stand-alone error checking tool for XML files extracted
from CCIS databases for submission to the Department for Education's NCCIS
service.

This tool currently conforms to the [2019-20 NCCIS MI Requirement](https://assets.publishing.service.gov.uk/government/uploads/system/uploads/attachment_data/file/758541/NCCIS_Management__Information_Requirement_2019_to_2020.pdf), and will check for all of the errors defined in Appendix D.

THIS TOOL IS UNDER DEVELOPMENT AND TESTING. It's functional, and is limited to
read-only operations on the XML input file. If you choose to test this
application, please be aware that it caches the submission data in an SQLite
database file called `CCISTool.db`. If this application is used to load a
submission file from a live database, this database file will contain all of
the personal data and sensitive personal data that are defined in the
Requirement. Care should be taken to destroy this database file properly after
use.

**Please do not rely on this application, or its output, at this time. The
author makes no warranty as to its fitness for purpose at this time.**

This application is licensed for use under the
[GNU General Public License](COPYING), version 3 (or later).
