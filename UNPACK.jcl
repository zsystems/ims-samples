//UNPACK   JOB ,                                                        00010001
// CLASS=A,MSGCLASS=H,MSGLEVEL=(1,1),NOTIFY=&SYSUID                     00020000
//***********************************************************@SCPYRT**  00030000
// EXPORT SYMLIST=(DSNTERSE,DSNCNTL)                                    00030101
// SET DSNTERSE='GEOFFN.DEMO.SETUP.TERSE'                               00030201
// SET DSNCNTL='GEOFFN.DEMO.SETUP.CNTL'                                 00030301
//***********************************************************@SCPYRT**  00031001
//DEL0     EXEC PGM=IDCAMS,REGION=1024K                                 00040000
//SYSPRINT DD   SYSOUT=*                                                00050000
//SYSIN    DD   *,SYMBOLS=(JCLONLY,SYSPRINT)                            00060001
 DELETE &DSNCNTL                                                        00090001
 SET MAXCC=0                                                            00100000
//*-------------------------------------------------------------------  00110000
//********************************************************************  00111000
//* UNTERSE THE DATASET                                                 00112000
//********************************************************************  00113000
//TERSE    EXEC PGM=AMATERSE,PARM=UNPACK                                00114000
//SYSPRINT DD SYSOUT=*,DCB=(RECFM=FBA,LRECL=133,BLKSIZE=12901)          00115000
//SYSUT1   DD DISP=SHR,DSN=&DSNTERSE                                    00116001
//SYSUT2   DD DISP=(NEW,PASS),DSN=&&UNTERSED,                           00117000
//            SPACE=(CYL,(10,5),RLSE)                                   00118000
//********************************************************************  00120000
//*  COPY FROM ONE DATASET TO ANOTHER                                   00130000
//********************************************************************  00140000
//COPY0    EXEC PGM=IEBCOPY,REGION=0M                                   00150000
//SYSIN    DD DUMMY                                                     00160000
//SYSPRINT DD SYSOUT=*                                                  00170000
//SYSUT1   DD DISP=(OLD,PASS),DSN=&&UNTERSED                            00180000
//SYSUT2   DD  DISP=(NEW,CATLG,DELETE),DSN=&DSNCNTL,                    00190001
//             SPACE=(CYL,(60,10,20),RLSE),STORCLAS=MEDIUM,             00200000
//             DCB=(RECFM=FB,LRECL=80,BLKSIZE=32000),                   00210000
//             DSNTYPE=LIBRARY                                          00220000
