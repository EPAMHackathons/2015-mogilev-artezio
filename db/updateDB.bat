@echo off


SETLOCAL
set NLS_LANG=AMERICAN_AMERICA.AL32UTF8


call liquibase %URL% --password=postgres --defaultSchemaName=public --defaultsFile=liquibase.properties --changeLogFile=1.0.xml update
IF NOT %ERRORLEVEL%  == 0 (
GOTO endscr)


ENDLOCAL
:endscr
echo %DATE% %TIME% update end
