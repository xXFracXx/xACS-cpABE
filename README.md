# xACS-cpABE

A practice school report submitted to MAHE in partial fulfilment of the requirement for the award of the degree of B.Tech in Computer Science & Engineering. Submitted by Shashank Pincha \[140905025\].

## Abstract

Attribute-based Encryption (ABE) is regarded as a promising cryptographic conducting tool to guarantee data owners’ direct control over their data in public cloud storage. The earlier ABE schemes involve only one authority to maintain the whole attribute set, which can bring a single-point bottleneck on both security and performance.  

Subsequently, some multi-authority schemes are proposed, in which multiple authorities separately maintain disjoint attribute subsets. However, the single-point bottleneck problem remains unsolved. In this paper, from another perspective, we conduct a threshold multi-authority CP-ABE access control scheme for public cloud storage, named xACS, in which multiple authorities jointly manage a uniform attribute set. In xACS, taking advantage of (t; n) threshold secret sharing, the master key can be shared among multiple authorities, and a legal user can generate his/her secret key by interacting with any t authorities.  

Security and performance analysis results show that xACS is not only verifiable secure when less than t authorities are compromised, but also robust when no less than t authorities are alive in the system. Furthermore, by efficiently combining the traditional multi-authority scheme with xACS, we construct a hybrid one, which satisfies the scenario of attributes coming from different authorities as well as achieving security and system-level robustness. 

---
## Table of Contents

- [1. Introduction](#1-introduction)
  * [1.1 Area of Work](#11-area-of-work)
  * [1.2 Present-day Scenario](#12-present-day-scenario)
  * [1.3 Motivation](#13-motivation)
    + [1.3.1 Shortcomings in Previous Work](#131-shortcomings-in-previous-work)
    + [1.3.2 Importance of Work in Present Context](#132-importance-of-work-in-present-context)
    + [1.3.3 Uniqueness of Adopted Methodology](#133-uniqueness-of-adopted-methodology)
  * [1.4 Objective](#14-objective)
  * [1.5 Target Speciﬁcations](#15-target-speciﬁcations)
    + [1.5.1 Hardware Requirments](#151-hardware-requirments)
    + [1.5.2 Software Requirments](#152-software-requirments)
- [2. Background Theory](#2-background-theory)
  * [2.1 Literature Review](#21-literature-review)
    + [2.1.1 Present State and Recent Developments](#211-present-state-and-recent-developments)
    + [2.1.2 Background Theory](#212-background-theory)
    + [2.1.3 Literature Survey](#213-literature-survey)
  * [2.2 Outcome of Literature Review](#22-outcome-of-literature-review)
- [3. Methodology](#3-methodology)
  * [3.1.1 Detailed Methodology](#311-detailed-methodology)
    + [3.1.2 Assumptions made](#312-assumptions-made)
    + [3.1.3 Design and Modelling](#313-design-and-modelling)
    + [3.1.4 Block Diagrams](#314-block-diagrams)
    + [3.1.5 Module Speciﬁcations](#315-module-speciﬁcations)
  * [3.2 Conclusions](#32-conclusions)
- [5. Conclusion and Future Scope](#5-conclusion-and-future-scope)
  * [5.1 Summary of Work](#51-summary-of-work)
    + [5.1.1 Problem Statement](#511-problem-statement)
    + [5.1.2 Adopted Methodology](#512-adopted-methodology)
  * [5.2 Conclusions](#52-conclusions)
  * [5.3 Future Scope](#53-future-scope)
- [6. References](#6-references)

---
## 1. Introduction

### 1.1 Area of Work

Cloud computing is the use of computing resources (hardware and software) that are delivered as a service over a network (typically the Internet). The name comes from the common use of a cloud-shaped symbol as an abstraction for the complex infrastructure it contains in system diagrams. Cloud computing entrusts remote services with a user's data, software and computation. Cloud computing consists of hardware and software resources made available on the Internet as managed third-party services. These services typically provide access to advanced software applications and high-end networks of server computers. 

Cloud storage, also called storage-as-a-service, on-line storage or utility storage, is a service model for data storage on a pay-per-use basis. Public cloud storage is often used for backing up data as part of a disaster recovery plan (DRP) as well as archiving email and static non-core application data. Usage is generally charged on a dollar-per-gigabyte-per-month basis, although some service providers may append data transfer and access charges for large enterprise customers. 

An advantage of using a public cloud for storage is that the provider is responsible for building and maintaining the storage infrastructure and its associated costs including power, cooling and server maintenance. The customer, who only pays for the resources that are being used, can scale storage space up or down on demand – in many cases, with just a simple click of the mouse. 

A disadvantage of using a public cloud for storage is that the customer turns control of his data over to a service provider. The public storage provider then becomes responsible for maintaining and protecting the data on its multi-tenancy infrastructure and assuring it remains secure during transfer to and from the provider’s facilities. If the provider suffers an outage, the data may not be accessible for the duration and if the provider suffers a catastrophic failure, there is always the risk that the data may be lost. 

In cryptography, encryption is the process of obscuring information to make it unreadable without special knowledge. This is usually done for secrecy, and typically for confidential communications. Encryption can also be used for authentication, digital signatures, digital cash etc. 

### 1.2 Present-day Scenario  

Attribute-based Encryption (ABE) is regarded as one of the most suitable schemes to conduct data access control in public cloud storage for it can guarantee data owners’ direct control over their data and provide a fine-grained access control service. Till now, there are many ABE schemes proposed, which can be divided into two categories: Key-Policy Attribute-based Encryption (KP-ABE) and Ciphertext-Policy Attribute-based Encryption (CP-ABE). 

In KP-ABE schemes, decrypt keys are associated with access structures while ciphertexts are only labelled with special attribute sets. On the contrary, in CP-ABE schemes, data owners can define an access policy for each file based on users’ attributes, which can guarantee owners’ more direct control over their data. Therefore, compared with KP-ABE, CP-ABE is a preferred choice for designing access control for public cloud storage.  

### 1.3 Motivation 

#### 1.3.1 Shortcomings in Previous Work

+ In most existing CP-ABE schemes there is only one authority responsible for attribute management and key distribution. This only-one-authority scenario can bring a single-point bottleneck on both security and performance. 

+ Once the authority is compromised, an adversary can easily obtain the only-one-authority’s master key, then he/she can generate private keys of any attribute subset to decrypt the specific encrypted data. 

+ Moreover, once the only-one-authority is crashed, the system completely cannot work well. 

+ Although some multi-authority CP-ABE schemes have been proposed, they still cannot deal with the problem of single-point bottleneck on both security and performance mentioned above. 

+ The adversary can obtain private keys of specific attributes by compromising specific one or more authorities. 

+ Crash or offline of a specific authority will make that private keys of all attributes in attribute subset maintained by this authority cannot be generated and distributed, which will still influence the whole system’s effective operation. 

#### 1.3.2 Importance of Work in Present Context  

+ xACS is not only verifiable secure when less than t authorities are compromised, but also robust when no less than t authorities are alive in the system. 

+ To the best of our knowledge, our base paper[1] is the first try to address the single point bottleneck on both security and performance in CPABE access control schemes in public cloud storage. 

+ In existing access control systems for public cloud storage, there brings a single-point bottleneck on both security and performance against the single authority for any specific attribute. 

+ By introducing the combining of (t; n) threshold secret sharing and multi-authority CP-ABE scheme, we propose and realize a robust and verifiable multi-authority access control system in public cloud storage, in which multiple authorities jointly manage a uniform attribute set. 

+ Furthermore, by efficiently combining the traditional multi-authority scheme with ours, we construct a hybrid one, which can satisfy the scenario of attributes coming from different authorities as well as achieving security and system-level robustness. 

#### 1.3.3 Uniqueness of Adopted Methodology  

Some solutions (practices, models, architectures etc.) used to handle cloud computing security issues are presented in academia. This paper identifies cloud computing security issues and solutions to handle these issues in software engineering context. 

We take two existing methods of data encryption, CP-ABE & (t, n) secret sharing. This combination provides us with a robust and secure Access Control System. Such a combination hasn’t been implemented before and the idea behind doing so is to identify all the existing solutions and best practices/solutions used in real-time to mitigate cloud computing security challenge and bridge the gap that stops enthusiastic users/organization from using the power of cloud computing. [2] 

### 1.4 Objective  

In this project, we propose a new threshold multi-authority CP-ABE access control scheme in cloud storage, in which all AAs jointly manage the whole attribute set and share the master key. Taking advantage of (t; n) threshold secret sharing, by interacting with any t AAs, a legal user can generate his/her secret key. Thus, xACS avoids any one AA being a single-point bottleneck on both security and performance. The main objectives can be outlined as follows: 

1. To implement a threshold multi-authority CP-ABE access control scheme using (t; n) threshold secret sharing by developing a web application called xACS to enable encrypted and secure file sharing. 

2. To create an access control scheme that is robust and secure with (t; n) threshold secret sharing. We can easily find appropriate values of (t; n) to make xACS not only secure when less than t authorities are compromised, but also robust when no less than t authorities are alive in the system. 

3. To deliver efficient and effective user experience by developing a responsive simple-to-use UI, built on bootstrap with HTML, CSS & JS components by deploying a scalable web application. 

### 1.5 Target Speciﬁcations  

#### 1.5.1 Hardware Requirments  

+ **System** Pentium Dual Core 
+ **RAM** 2 GB 
+ **Input Devices** Keyboard, Mouse
+ **Storage** 120 GB 

#### 1.5.2 Software Requirments  

+ **Operating Software** Windows 10 
+ **Coding Language** JAVA / J2EE 
+ **IDE** Eclipse 
+ **Data Base** SQL 
+ **3rd Party Software:** 
  + mySQL (Database & Interface) \[ver. 1.7\] 
  + Java Development Kit \[ver. 8u151\] 
  + Java Runtime Environment \[ver. 8u151\] 
  + Navicat GUI \[ver. 120\] 
  + Eclipse IDE \[ver. Oxygen\] 
  + wamp Server \[ver. 3.1.0\]

---
## 2. Background Theory

### 2.1 Literature Review 

#### 2.1.1 Present State and Recent Developments  

To satisfy requirements of data storage and high performance computation, cloud computing has drawn extensive attentions from both academic and industry. Cloud storage is an important service of cloud computing, which provides services for data owners to outsource data to store in cloud via Internet. Despite many advantages of cloud storage, there still remain various challenging obstacles, among which, privacy and security of users’ data have become major issues, especially in public cloud storage. 

Traditionally, a data owner stores his/her data in trusted servers, which are generally controlled by a fully trusted administrator. However, in public cloud storage systems, the cloud is usually maintained and managed by a semi-trusted third party (the cloud provider). Data is no longer in data owner’s trusted domains and the data owner cannot trust on the cloud server to conduct secure data access control. Therefore, the secure access control problem has become a critical challenging issue in public cloud storage, in which traditional security technologies cannot be directly applied. [3] 

#### 2.1.2 Background Theory  

Attribute-based Encryption (ABE) is regarded as one of the most suitable schemes to conduct data access control in public clouds for it can guarantee data owners’ direct control over their data and provide a fine-grained access control service. Till now, there are many ABE schemes proposed, which can be divided into two categories: Key-Policy Attribute-based Encryption (KP-ABE) and Ciphertext-Policy Attribute-based Encryption (CP-ABE).  

In KP-ABE schemes, decrypt keys are associated with access structures while ciphertexts are only labelled with special attribute sets. On the contrary, in CP-ABE schemes, data owners can define an access policy for each file based on users’ attributes, which can guarantee owners’ more direct control over their data. Therefore, compared with KP-ABE, CP-ABE is a preferred choice for designing access control for public cloud storage. In most existing CP-ABE schemes there is only one authority responsible for attribute management and key distribution.  

This only-one-authority scenario can bring a single-point bottleneck on both security and performance. Once the authority is compromised, an adversary can easily obtain the only-one-authority’s master key, then he/she can generate private keys of any attribute subset to decrypt the specific encrypted data. Moreover, once the only-one-authority is crashed, the system completely cannot work well. Therefore, these CP-ABE schemes are still far from being widely used for access control in public cloud storage.  

Although some multi-authority CP-ABE schemes have been proposed, they still cannot deal with the problem of single-point bottleneck on both security and performance mentioned above. In these multi-authority CP-ABE schemes, the whole attribute set is divided into multiple disjoint subsets and each attribute subset is still maintained by only one authority. Although the adversary cannot gain private keys of all attributes if he/she hasn’t compromised all authorities, compromising one or more authorities would make the adversary have more privileges than he/she should have. Moreover, the adversary can obtain private keys of specific attributes by compromising specific one or more authorities.  

In addition, the single point bottleneck on performance is not yet solved in these multi-authority CP-ABE schemes. Crash or offline of a specific authority will make that private keys of all attributes in attribute subset maintained by this authority cannot be generated and distributed, which will still influence the whole system’s effective operation.  

In this paper, we propose a robust and verifiable threshold multi-authority CP-ABE access control scheme, named xACS, to deal with the single-point bottleneck on both security and performance in most existing schemes. In xACS, multiple authorities jointly manage the whole attribute set but no one has full control of any specific attribute. Since in CP-ABE schemes, there is always a secret key (SK) used to generate attribute private keys, we introduce (t; n) threshold secret sharing into our scheme to share the secret key among authorities. In TMACS, we redefine the secret key in the traditional CP-ABE schemes as master key. The introduction of (t; n) threshold secret sharing guarantees that the master key cannot be obtained by any authority alone. TMACS is not only verifiable secure when less than t authorities are compromised, but also robust when no less than t authorities are alive in the system. To the best of our knowledge, this paper is the first try to address the singlepoint bottleneck on both security and performance in CPABE access control schemes in public cloud storage. 

#### 2.1.3 Literature Survey  

**Ciphertext-Policy Attribute-Based Encryption –**

In the ciphertext-policy attribute-based encryption scheme, each user's private key (decryption key) is tied to a set of attributes representing that user's permissions. When a ciphertext is encrypted, a set of attributes is designated for the encryption, and only users tied to the relevant attributes are able to decrypt the ciphertext. Most existing public key encryption methods allow a party to encrypt data to a particular user, but are unable to efficiently handle more expressive types of encrypted access control. [4] 

An ciphertext-policy attribute based encryption scheme consists of four fundamental algorithms: Setup, Encrypt, KeyGen, and Decrypt. In addition, we allow for the option of a fifth algorithm Delegate. [5] 

**Setup.** The setup algorithm takes no input other than the implicit security parameter. It outputs the public parameters PK and a master key MK.  

**Encrypt(PK,M, A).** The encryption algorithm takes as input the public parameters PK, a message M, and an access structure A over the universe of attributes. The algorithm will encrypt M and produce a ciphertext CT such that only a user that possesses a set of attributes that satisfies the access structure will be able to decrypt the message. We will assume that the ciphertext implicitly contains A.  

**Key Generation(MK,S).** The key generation algorithm takes as input the master key MK and a set of attributes S that describe the key. It outputs a private key SK.  

**Decrypt(PK, CT, SK).** The decryption algorithm takes as input the public parameters PK, a ciphertext CT, which contains an access policy A, and a private key SK, which is a private key for a set S of attributes. If the set S of attributes satisfies the access structure A then the algorithm will decrypt the ciphertext and return a message M. 

**(t, n) Secret Sharing –** 

Secret sharing (also called secret splitting) refers to methods for distributing a secret amongst a group of participants, each of whom is allocated a share of the secret. The secret can be reconstructed only when a sufficient number, of possibly different types, of shares are combined together; individual shares are of no use on their own. 

In one type of secret sharing scheme there is one dealer and n players. The dealer gives a share of the secret to the players, but only when specific conditions are fulfilled will the players be able to reconstruct the secret from their shares. The dealer accomplishes this by giving each player a share in such a way that any group of t (for threshold) or more players can together reconstruct the secret but no group of fewer than t players can. Such a system is called a (t, n)-threshold scheme (sometimes it is written as an (n, t)-threshold scheme). [6] 

Shamir's Secret Sharing is an algorithm in cryptography created by Adi Shamir. It is a form of secret sharing, where a secret is divided into parts, giving each participant its own unique part, where some of the parts or all of them are needed in order to reconstruct the secret. 

Counting on all participants to combine the secret might be impractical, and therefore sometimes the threshold scheme is used where any k of the parts are sufficient to reconstruct the original secret. 

The goal is to divide secret S (that is, a safe combination) into n pieces of data S1 ….. Sn in such a way that: 

+ Knowledge of any k or more Si pieces makes S easily computable. That is, the complete secret S can be reconstructed from any combination of k pieces of data. 

+ Knowledge of any k-1 or fewer Si pieces leaves S completely undetermined, in the sense that the possible values for S seem as likely as with knowledge of 0 pieces. Said another way, the secret S cannot be reconstructed with fewer than k pieces. 

This scheme is called (k, n) threshold scheme. If k=n then every piece of the original secret S is required to reconstruct the secret. [7] 

### 2.2 Outcome of Literature Review  

Through the literature review we have gained a better understanding of existing technologies and methods in access control systems. Enabling us to create a new ACS implementing CP-ABE and (t, n) Secret Sharing, thus creating a Robust and Verifiable Threshold Multi-Authority Access Control System. 

---
## 3. Methodology

### 3.1.1 Detailed Methodology

**xACS –**

The xACS multiple authorities jointly manage the whole attribute set but no one has full control of any specific attribute. In xACS, a global certificate authority is responsible for the construction of the system, which avoids the extra overhead caused by AAs’ negotiation of system parameters. CA is also responsible for the registration of users, which avoids AAs synchronized maintaining a list of users. However, CA is not involved in AAs’ master key sharing and users’ secret key generation, which avoids CA becoming the security vulnerability and performance bottleneck. 

Design of xACS is reusing of the master key shared among multiple attribute authorities. In traditional (t; n) threshold secret sharing, once the secret is reconstructed among multiple participants, someone can gain its value. Similarly, in CP-ABE schemes, the only-one-authority knows the master key and uses it to generate each user’s secret key according to a specific attribute set. 

In this case, if the AA is compromised by an adversary, it will become the security vulnerability. To avoid this, by means of (t; n) threshold secret sharing, the master key cannot be individually reconstructed and gained by any entity in xACS.hat the master key a is secure. By this means, we solve the problem of reusing of the master key. 

**Data Access Control Scheme –** 

We propose a robust and verifiable threshold multi-authority CP-ABE access control scheme, named xACS, to deal with the single-point bottleneck on both security and performance in most existing schemes. In xACS, multiple authorities jointly manage the whole attribute set but no one has full control of any specific attribute. Since in CP-ABE schemes, there is always a secret key (SK) used to generate attribute private keys, we introduce (t; n) threshold secret sharing into our scheme to share the secret key among authorities. 

In xACS, we redefine the secret key in the traditional CP-ABE schemes as master key. The introduction of (t; n) threshold secret sharing guarantees that the master key cannot be obtained by any authority alone. xACS is not only verifiable secure when less than t authorities are compromised, but also robust when no less than t authorities are alive in the system. 

**Certificate Authority –** 

The certificate authority is a global trusted entity in the system that is responsible for the construction of the system by setting up system parameters and attribute public key (PK) of each attribute in the whole attribute set. CA accepts users and AAs’ registration requests by assigning a unique uid for each legal user and a unique aid for each AA. CA also decides the parameter t about the threshold of AAs that are involved in users’ secret key generation for each time. 

However, CA is not involved in AAs’ master key sharing and users’ secret key generation. Therefore, for example, CA can be government organizations or enterprise departments which are responsible for the registration. Certificate authority is responsible for the construction of the system, which avoids the extra overhead caused by AAs’ negotiation of system parameters. CA is also responsible for the registration of users, which avoids AAs synchronized maintaining a list of users. 

**Attribute Authorities –** 

AAs take part of the responsibility to construct the system, and they can be the administrators or the managers of the application system. 

Different from other existing multi-authority CP-ABE systems, all AAs jointly manage the whole attribute set; however, any one of AAs cannot assign users’ secret keys alone for the master key is shared by all AAs. All AAs cooperate with each other to share the master key. By this means, each AA can gain a piece of master key shares its private key, then each AA sends its corresponding public key to CA to generate one of the system public keys. 

When it comes to generate users’ secret key, each AA only should generate its corresponding secret key independently. The master key shared among multiple attribute authorities. In traditional (t; n) threshold secret sharing, once the secret is reconstructed among multiple participants, someone can gain its value. 

#### 3.1.2 Assumptions made  

In multi-authority public cloud storage systems, the security assumption of the five roles is assumed as follows. The cloud server is always online and managed by the cloud provider. Usually, the cloud server and its provider is assumed “honest-but-curious”. In actually using this model, there exist different assumptions about whether the cloud server can collude with the malicious users. Thus in order to eliminate the ambiguous, in this paper, we assume that the cloud server can still collude with some malicious users to gain the content of encrypted data when it is highly beneficial. Moreover, if treating this as a relative strong security assumption, the scheme meeting the security requirements can also apply to the scenario with a relative weak security assumption that the cloud server will never collude with malicious users. CA is assumed to be trusted, but it can also be compromised by an adversary, so as to AAs. Although a user can freely get ciphertexts from the cloud server, he/she can’t decrypt the encrypted data only unless the user’s attributes satisfy the access policy hidden inside the encrypted data. Therefore, some malicious users are assumed to be dishonest and curious, who may collude with other entities except data owners(even compromising AAs) to obtain the access permission beyond their privileges. As a comparison, owners can be fully trusted. 

#### 3.1.3 Design and Modelling  

In robust multi-authority public cloud storage systems, there exist five entities: a global certificate authority (CA), multiple attribute authorities (AAs), data owners (Owners), data consumers (Users), and the cloud server.  

1. The certificate authority is a global trusted entity in the system that is responsible for the construction of the system by setting up system parameters and attribute public key (PK) of each attribute in the whole attribute set. CA accepts users and AAs’ registration requests by assigning a unique uid for each legal user and a unique aid for each AA. CA also decides the parameter t about the threshold of AAs that are involved in users’ secret key generation for each time. However, CA is not involved in AAs’ master key sharing and users’ secret key generation. Therefore, for example, CA can be government organizations or enterprise departments which are responsible for the registration.  

2. The attribute authorities focus on the task of attribute management and key generation. Besides, AAs take part of the responsibility to construct the system, and they can be the administrators or the managers of the application system. Different from other existing multi-authority CP-ABE systems, all AAs jointly manage the whole attribute set, however, any one of AAs cannot assign users’ secret keys alone for the master key is shared by all AAs. All AAs cooperate with each other to share the master key. By this means, each AA can gain a piece of master key share as its private key, then each AA sends its corresponding public key to CA to generate one of the system public keys. When it comes to generate users’ secret key, each AA only should generate its corresponding secret key independently. That is to say, no communication among AAs is needed in the phase of users’ secret key generation.  

3. The data owner (Owner) encrypts his/her file and defines access policy about who can get access to his/her data. First of all, each owner encrypts his/her data with a symmetric encryption algorithm like AES and DES. Then the owner formulates access policy over an attribute set and encrypts the symmetric key under the policy according to attribute public keys gained from CA. Here, the symmetric key is the key used in the former process of symmetric encryption. After that, the owner sends the whole encrypted data and the encrypted symmetric key to store in the cloud server. However, the owner doesn’t rely on the cloud server to conduct data access control. Data stored in the cloud server can be gained by any data consumer. Despite all this, no data consumer can gain the plaintext without the attribute set satisfying the access policy.  

4. The data consumer (User) is assigned with a global user identity uid from CA, and applies for his/her secret keys from AAs with his/her identification. The user can freely get the ciphertexts that he/she is interested in from the cloud server. He/She can decrypt the encrypted data if and only if his/her attribute set satisfies the access policy hidden inside the encrypted data. 5) The cloud server does nothing but provide a platform for owners storing and sharing their encrypted data. The cloud server doesn’t conduct data access control for owners. The encrypted data stored in the cloud server can be downloaded freely by any data consumer. 

We can see an overview of the system’s architecture in fig 3.1. 

![fig 3.1](https://www.dl.dropboxusercontent.com/s/0cglqrymq2ctapw/sa.jpg)

*Fig. 3.1 System Architecture*

1. AA registers to CA to gain AID & AID.cert 
2. User registers to CA to gain UID & UID.cert 
3. User gains his/her SK from any t out of n AAs 
4. Owners gain PK from CA 
5. Owners upload to the Cloud server 
6. Users download from the Cloud server 

#### 3.1.4 Block Diagrams  

**Data Flow Diagram –**

The data flow diagram (DFD) is a simple graphical formalism that can be used to represent a system in terms of input data to the system, various processing carried out on this data, and the output data is generated by this system. 

These components are the system process, the data used by the process, an external entity that interacts with the system and the information flows in the system. 

DFD shows how the information moves through the system and how it is modified by a series of transformations. It is a graphical technique that depicts information flow and the transformations that are applied as data moves from input to output. Figure 3.2 is xACS’s Data Flow Diagram. 

![fig 3.2](https://www.dl.dropboxusercontent.com/s/w9gnjsh2fqnvxm1/xACS%20Data%20Flow.png)

*Fig. 3.2 Data Flow Diagram* 

**Class Diagram –** 

In software engineering, a class diagram in the Unified Modeling Language (UML) is a type of static structure diagram that describes the structure of a system by showing the system's classes, their attributes, operations (or methods), and the relationships among the classes. It explains which class contains information. Figure 3.3 is xACS’s Class Diagram. 

![fig 3.3](https://www.dl.dropboxusercontent.com/s/wa4d9suor1q8og5/xACS%20Class%20Dia.png)

*Fig. 3.3 Class Diagram* 

**Sequence Diagram –** 

A sequence diagram in Unified Modelling Language (UML) is a kind of interaction diagram that shows how processes operate with one another and in what order. It is a construct of a Message Sequence Chart. Sequence diagrams are sometimes called event diagrams, event scenarios, and timing diagrams. Figure 3.4 is xACS’s Sequence Diagram. 

![fig 3.4](https://www.dl.dropboxusercontent.com/s/qr703u6c8d5icmm/xACS%20Sequence%20Dia.png)

*Fig. 3.4 Sequence Diagram* 

**Activity Diagram –**

Activity diagrams are graphical representations of workflows of stepwise activities and actions with support for choice, iteration and concurrency. In the UML, activity diagrams can be used to describe the business and operational step-by-step workflows of components in a system. An activity diagram shows the overall flow of control. Figure 3.5 is xACS’s Activity Diagram.  

![fig 3.5](https://www.dl.dropboxusercontent.com/s/d9pfy0mafe3pj8k/xACS%20Activity%20Dia.png)

*Fig. 3.5 Activity Diagram* 

#### 3.1.5 Module Speciﬁcations  

**SQL Database –** 

The user data and files are currently stored on the SQL database running on the localhost WAMP server. The stored records and files are retrieved through SQL commands and connections from the Java servlet modules. The 4 tables reg, request, file_upload & download contain records about the accounts, file request, uploaded files and downloads history respectively. Refer figure 3.6 & 3.7 for the DB Schema. 

![fig 3.6](https://www.dl.dropboxusercontent.com/s/r0rgemo04agqatl/xacs_db.png)

*Fig. 3.6 Cloud DB Schema* 

![fig 3.7](https://www.dl.dropboxusercontent.com/s/wyoixokhai1wun7/xacs_aa.png)

*Fig. 3.7 AA DB Schema* 

**SQL Table creation query(s) –**

```sql
CREATE TABLE IF NOT EXISTS `aareqs` ( 
  `id` int(10) NOT NULL, 
  `name` varchar(45) NOT NULL, 
  `mail` varchar(45) NOT NULL, 
  `state` varchar(45) NOT NULL, 
  `country` varchar(45) NOT NULL, 
  `fname` varchar(45) NOT NULL, 
  `status` varchar(45) NOT NULL, 
  PRIMARY KEY (`id`) 
) ENGINE=MyISAM DEFAULT CHARSET=latin1; 
 
CREATE TABLE IF NOT EXISTS `aashares` ( 
  `UID` varchar(45) NOT NULL, 
  `subShare` mediumtext NOT NULL, 
  PRIMARY KEY (`UID`) 
) ENGINE=MyISAM DEFAULT CHARSET=latin1; 

CREATE TABLE IF NOT EXISTS `aalist` ( 
  `aaNum` int(10) NOT NULL AUTO_INCREMENT, 
  `AID` varchar(45) NOT NULL, 
  PRIMARY KEY (`aaNum`) 
) ENGINE=MyISAM AUTO_INCREMENT=2 DEFAULT CHARSET=latin1; 

CREATE TABLE IF NOT EXISTS `acs_info` ( 
  `id` enum('1') NOT NULL, 
  `sysname` varchar(26) NOT NULL, 
  `n` int(11) NOT NULL, 
  `t` int(11) NOT NULL, 
  `public_key` varchar(800) NOT NULL, 
  `master_key` varchar(300) NOT NULL, 
  `PKFile` longblob, 
  `MKFile` longblob, 
  PRIMARY KEY (`id`) 
) ENGINE=InnoDB DEFAULT CHARSET=latin1; 

CREATE TABLE IF NOT EXISTS `download` ( 
  `id` int(10) UNSIGNED NOT NULL AUTO_INCREMENT, 
  `filename` varchar(45) NOT NULL, 
  `username` varchar(45) NOT NULL, 
  `time` varchar(45) NOT NULL, 
  PRIMARY KEY (`id`) 
) ENGINE=InnoDB DEFAULT CHARSET=latin1; 

CREATE TABLE IF NOT EXISTS `file_upload` ( 
  `id` int(10) UNSIGNED NOT NULL AUTO_INCREMENT, 
  `filename` varchar(220) NOT NULL, 
  `encFile` longblob NOT NULL, 
  `owner` varchar(45) NOT NULL, 
  `time` varchar(45) NOT NULL, 
  `encAesKey` longblob NOT NULL, 
  PRIMARY KEY (`id`) 
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=latin1; 

CREATE TABLE IF NOT EXISTS `reg` ( 
  `id` int(10) UNSIGNED NOT NULL AUTO_INCREMENT, 
  `name` varchar(45) NOT NULL, 
  `pass` varchar(45) NOT NULL, 
  `email` varchar(45) NOT NULL, 
  `dob` varchar(45) NOT NULL, 
  `gen` varchar(45) NOT NULL, 
  `role` varchar(45) NOT NULL, 
  `state` varchar(45) NOT NULL, 
  `country` varchar(45) NOT NULL, 
  `status` varchar(45) NOT NULL, 
  `audd` varchar(45) DEFAULT NULL, 
  `joinDate` timestamp NULL DEFAULT CURRENT_TIMESTAMP, 
  `phone` varchar(10) DEFAULT NULL, 
  PRIMARY KEY (`id`) 
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=latin1; 

CREATE TABLE IF NOT EXISTS `request` ( 
  `id` int(10) UNSIGNED NOT NULL AUTO_INCREMENT, 
  `name` varchar(45) NOT NULL, 
  `mail` varchar(45) NOT NULL, 
  `state` varchar(45) NOT NULL, 
  `country` varchar(45) NOT NULL, 
  `fname` varchar(45) NOT NULL, 
  `status` varchar(45) NOT NULL, 
  `aCount` int(11) NOT NULL DEFAULT '0', 
  `aa1Share` mediumtext, 
  `aa2Share` mediumtext, 
  `aa3Share` mediumtext, 
  `aa4Share` mediumtext, 
  `aa5Share` mediumtext, 
  `aa6Share` mediumtext, 
  `aa7Share` mediumtext, 
  `aa8Share` mediumtext, 
  `aa9Share` mediumtext, 
  `aa10Share` mediumtext, 
  PRIMARY KEY (`id`) 
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=latin1; 
```

**Backend –** 

Following the system implementation analysis, I created a JSP development environment in NetBeans, and began working on various Java servlet(s) for the various classes & functions required. We moved to the Eclipse IDE down the line. 

Source Packages / File Structure -  

![file structure](https://www.dl.dropboxusercontent.com/s/igypuonj4a9uypp/Screen%20Shot%202018-07-07%20at%204.40.36%20PM.png)

**Frontend -**

The system’s users will only be interreacting with the frontend. It is developed through HTML, JS & CSS primarily. We created JSP pages for each user and implemented the java Servlets accordingly. 

Web Pages / File Structure - 

![file structure](https://www.dl.dropboxusercontent.com/s/i421lyxdl3aqfo7/Screen%20Shot%202018-07-07%20at%204.47.51%20PM.png)

**Assets Folder –** 

The Assets folder contains all the building blocks for the site, it consists of the base CSS and JavaScript files, referred from the CSS and JS folders respectively. The site is built on a Bootstrap framework. The favicon and the various images throughout are also saved in the assets folder. The vendor folder consists of 3rd party JS plugins that are used in the project. 

**_index.jsp –_** 

This page is the landing page for our system, here anyone can signup for an account or log into the same. Figure 3.8 is a screenshot of login.jsp.  

![fig 3.8](https://www.dl.dropboxusercontent.com/s/zpawpvigxmjofl8/login_jsp.png)

*Fig. 3.8 index.jsp* 

**_signup.jsp –_** 

On this page, user’s can create three types of accounts: owner, user, AA. The details entered are added to the SQL DB. Once activated by a CA, the user & AA accounts can be accessed. Figure 3.9 is a screenshot of signup.jsp.  

![fig 3.9](https://www.dl.dropboxusercontent.com/s/mueh41yvlm0kegc/signup_jsp.png) 

*Fig. 3.9 signup.jsp* 

The signup.jsp page redirects the user to otp.jsp where they are required to enter an OTP, sent to the their mobile number, to complete the sign up process. 

**AA Files –** 

**_file_details.jsp –_** 

Here, the aa can see all uploaded files and their details. Figure 3.10 is a screenshot of aa/file_details.jsp.  

![fig 3.10](https://www.dl.dropboxusercontent.com/s/h7rd7zfc2gq6nws/aa_file_details.png)  

*Fig. 3.10 aa/file_details.jsp* 

**_user_requests.jsp –_** 

Here, the aa can see all user request and their details. Figure 3.11 is a screenshot of aa/user_requests.jsp.  

![fig 3.11](https://www.dl.dropboxusercontent.com/s/0mw06u1lkfbqmwe/aa_user_requests.png) 

*Fig. 3.11 aa/user_requests.jsp* 

**CA Files –** 

**_Dashboard.jsp –_** 

Here the CA can see recent signups, activate them directly from the dashboard if required. The CA is able to reset/initialize the system from here. Figure 3.12 is a screenshot of ca/dashboard.jsp. 

![fig 3.12](https://www.dl.dropboxusercontent.com/s/5i4vcg925svoecg/ca_dashboard.png) 

*Fig. 3.12 ca/dashboard.jsp* 

**_auth_details.jsp –_** 

Here, the CA can see already activated/authorized AAs and Users. Figure 3.13 is a screenshot of ca/auth_details.jsp. 

![fig 3.13](https://www.dl.dropboxusercontent.com/s/f2w1i7d5rfmsr5e/ca_auth_details.png)  

*Fig. 3.13 ca/auth_details.jsp* 

**_auth_pending.jsp –_** 

Here, the CA can activate/authorize Users and AAs. Figure 3.14 is a screenshot of ca/auth_pending.jsp. 

![fig 3.14](https://www.dl.dropboxusercontent.com/s/lnvgszv2rpqwrs5/ca_auth_pending.png)   

*Fig. 3.14 ca/auth_pending.jsp* 

**Cloud Files –** 

**_dl_details.jsp –_** 

Here, the cloud can see the history of all file downloads. Figure 3.16 is a screenshot of cloud/dl_detials.jsp.  

![fig 3.15](https://www.dl.dropboxusercontent.com/s/bty6iv7kb9w9jj3/cloud_dl_details.png)

*Fig. 3.15 cloud/dl_details.jsp* 

**_file_details.jsp –_** 

Here, the cloud can see all uploaded files and their details. Figure 3.16 is a screenshot of cloud/file_details.jsp. 

![fig 3.16](https://www.dl.dropboxusercontent.com/s/vxu350gql5ih2z2/cloud_file_details.png) 

*Fig. 3.16 cloud/file_details.jsp* 

**Owner Files –** 

**_file_details.jsp –_** 

Here, the owner can see all his/her uploaded files and their details. Figure 3.17 is a screenshot of owner/file_details.jsp. 

![fig 3.17](https://www.dl.dropboxusercontent.com/s/fs9wyk8yzl71nfb/owner_file_details.png) 

*Fig. 3.17 owner/file_details.jsp* 

**_file_upload.jsp –_** 

Here, the owner can upload files. These files will be available to download to Users that matches the requirements. Figure 3.18 is a screenshot of owner/file_upload.jsp. 

![fig 3.18](https://www.dl.dropboxusercontent.com/s/b2awju0cg2k8nq3/owner_file_upload.png) 

*Fig. 3.18 owner/file_upload.jsp* 

**User Files –** 

**_file_download.jsp –_** 

Here, the user can download files. These files will be available to download to if the user’s SK matches the file’s set policy. Figure 3.19 is a screenshot of user/file_download.jsp. 

![fig 3.19](https://www.dl.dropboxusercontent.com/s/vtahybvy7uxirtb/user_file_download.png) 

*Fig. 3.19 user/file_download.jsp* 

**_file_request.jsp –_** 

Here, the user can request files. Figure 3.20 is a screenshot of user/file_request.jsp. 

![fig 3.20](https://www.dl.dropboxusercontent.com/s/0qjydcypmiqnmle/user_file_request.png) 

*Fig. 3.20 user/file_request.jsp* 

### 3.2 Conclusions 

The analysis results show that our access control scheme is robust and secure. We can easily find appropriate values of (t; n) to make xACS not only secure when less than t authorities are compromised, but also robust when no less than t authorities are alive in the system. Furthermore, based on efficiently combining the traditional multi-authority scheme with xACS, we also construct a hybrid scheme that is more suitable for the real scenario, in which attributes come from different authority-sets and multiple authorities in an authority-set jointly maintain a subset of the whole attribute set. This enhanced scheme addresses not only attributes coming from different authorities but also security and system-level robustness. 

---
## 5. Conclusion and Future Scope 

### 5.1 Summary of Work 

#### 5.1.1 Problem Statement  

Attribute-based Encryption (ABE) is regarded as a promising cryptographic conducting tool to guarantee data owners’ direct control over their data in public cloud storage. The earlier ABE schemes involve only one authority to maintain the whole attribute set, which can bring a single-point bottleneck on both security and performance.  

Subsequently, some multi-authority schemes are proposed, in which multiple authorities separately maintain disjoint attribute subsets. However, the single-point bottleneck problem remains unsolved. In this paper, from another perspective, we conduct a threshold multi-authority CP-ABE access control scheme for public cloud storage, named xACS, in which multiple authorities jointly manage a uniform attribute set. In xACS, taking advantage of (t; n) threshold secret sharing, the master key can be shared among multiple authorities, and a legal user can generate his/her secret key by interacting with any t authorities.   

#### 5.1.2 Adopted Methodology  

The two main methodologies focused on in this paper are Cipherpolicy-text Attribute Based Encryption and (t, n) Secret Sharing, combining these two we create a robust and verifiable threshold Multi-Authority Access Control System. 

### 5.2 Conclusions 

The analysis results show that our access control scheme is robust and secure. We can easily find appropriate values of (t; n) to make xACS not only secure when less than t authorities are compromised, but also robust when no less than t authorities are alive in the system. Furthermore, based on efficiently combining the traditional multi-authority scheme with xACS, we also construct a hybrid scheme that is more suitable for the real scenario, in which attributes come from different authority-sets and multiple authorities in an authority-set jointly maintain a subset of the whole attribute set. This enhanced scheme addresses not only attributes coming from different authorities but also security and system-level robustness. 

### 5.3 Future Scope 

How to reasonably select the values of (t; n) in theory and design optimized interaction protocols will be addressed in our future work. 

---
## 6. References 

[1] W. Li, K. Xue, Y. Xue and J. Hong, "TMACS: A Robust and Verifiable Threshold Multi-Authority Access Control System in Public Cloud Storage", IEEE Transactions on Parallel and Distributed Systems, vol. 27, no. 5, 2016. 

[2] S. Bulusu and K. Sudia, "A Study on Cloud Computing Security Challenges", vol. -201282, 2012. 

[3] M. More and S. Gaikwad, "A Robust Threshold Multi-Authority Access Control System in Public Cloud Storage", IJARCSMS, vol. 5, no. 11, 2017. 

[4] "Ciphertext-Policy Attribute-Based Encryption (CPABE)", Cs.virginia.edu, 2014. [Online]. Available: https://www.cs.virginia.edu/~shelat/14s-pet/2014/03/31/ciphertext-policy-attribute-based-encryption-cpabe.html. 

[5] J. Bethencourt, A. Sahai and B. Waters, "Ciphertext-Policy Attribute-Based Encryption." 

[6] "Secret sharing", En.wikipedia.org, 2018. [Online]. Available: https://en.wikipedia.org/wiki/Secret_sharing. 

[7] "Shamir's Secret Sharing", En.wikipedia.org, 2018. [Online]. Available: https://en.wikipedia.org/wiki/Shamir%27s_Secret_Sharing. 
