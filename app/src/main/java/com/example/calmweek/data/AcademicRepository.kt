package com.example.calmweek.data

import com.example.calmweek.model.*

class AcademicRepository {
    fun getSemesters(): List<Semester> {
        return listOf(
            Semester("sem1", "Semester 1", 1, 5, 0),
            Semester("sem2", "Semester 2", 2, 5, 0),
            Semester("sem3", "Semester 3", 3, 5, 0),
            Semester("sem4", "Semester 4", 4, 5, 0),
            Semester("sem5", "Semester 5", 5, 5, 0),
            Semester("sem6", "Semester 6", 6, 5, 0),
            Semester("sem7", "Semester 7", 7, 4, 0),
            Semester("sem8", "Semester 8", 8, 2, 0)
        )
    }

    fun getSubjectsForSemester(semesterId: String): List<Subject> {
        return when (semesterId) {
            "sem1" -> listOf(
                Subject("pps", "Programming for Problem Solving", "sem1", "C Programming, Arrays, Pointers, Structures, File Handling", "", 1, true, 0, 5),
                Subject("math1", "Engineering Mathematics-I", "sem1", "Calculus, Ordinary Differential Equations, Matrices", "", 2, true, 0, 3),
                Subject("phy", "Engineering Physics", "sem1", "Quantum Physics, Wave Optics, Lasers", "", 3, true, 0, 3),
                Subject("bee", "Basic Electrical Engineering", "sem1", "DC/AC Circuits, Transformers, Electrical Machines", "", 4, true, 0, 3),
                Subject("comm", "Communication Skills", "sem1", "Professional communication, Technical writing, Grammar", "", 5, true, 0, 2)
            )
            "sem2" -> listOf(
                Subject("oop_cpp", "Object Oriented Programming in C++", "sem2", "Classes, Objects, Inheritance, Polymorphism, Templates", "", 1, true, 0, 4),
                Subject("math2", "Engineering Mathematics-II", "sem2", "Multiple Integrals, Vector Calculus, Laplace Transforms", "", 2, true, 0, 3),
                Subject("chem", "Engineering Chemistry", "sem2", "Water treatment, Electrochemistry, Polymers, Spectroscopy", "", 3, true, 0, 3),
                Subject("dld", "Digital Logic Design", "sem2", "Boolean Algebra, Logic Gates, Flip-Flops, Counters", "", 4, true, 0, 3),
                Subject("python", "Python Programming", "sem2", "Python basics, Data structures, Libraries, Scripting", "", 5, true, 0, 4)
            )
            "sem3" -> listOf(
                Subject("ds", "Data Structures & Algorithms", "sem3", "Arrays, Stacks, Queues, Trees, Graphs with Striver & Love Babbar", "", 1, true, 0, 3),
                Subject("java", "Object Oriented Programming (Java)", "sem3", "Core Java, Collections Framework, Multithreading", "", 2, true, 0, 3),
                Subject("co", "Computer Organization & Architecture", "sem3", "Instruction Set Architecture, ALU, Pipeline", "", 3, true, 0, 3),
                Subject("discrete", "Discrete Mathematics", "sem3", "Sets, Relations, Graph Theory, Combinatorics", "", 4, true, 0, 3),
                Subject("web_basic", "Web Development Basics", "sem3", "HTML5, CSS3, JavaScript, Responsive Design", "", 5, true, 0, 3)
            )
            "sem4" -> listOf(
                Subject("daa", "Design and Analysis of Algorithms", "sem4", "Sorting, Greedy, Dynamic Programming, Graph Algorithms", "", 1, true, 0, 3),
                Subject("toc", "Theory of Computation", "sem4", "Automata, Regular Expressions, Context-Free Grammars", "", 2, true, 0, 3),
                Subject("os", "Operating Systems", "sem4", "CPU Scheduling, Deadlocks, Memory Management, File Systems", "", 3, true, 0, 2),
                Subject("se", "Software Engineering", "sem4", "Agile, Scrum, Testing, UML Diagrams", "", 4, true, 0, 3),
                Subject("java_adv", "Advanced Java & Frameworks", "sem4", "Spring Boot, Servlets, JSP, Hibernate", "", 5, true, 0, 3)
            )
            "sem5" -> listOf(
                Subject("dbms", "Database Management Systems", "sem5", "ER Model, Relational Algebra, SQL, Normalization, Transactions", "", 1, true, 0, 2),
                Subject("cn", "Computer Networks", "sem5", "OSI & TCP/IP Models, Routing Protocols, TCP/UDP, DNS", "", 2, true, 0, 1),
                Subject("ai", "Artificial Intelligence", "sem5", "State Space Search, Heuristics, Knowledge Representation", "", 3, true, 0, 3),
                Subject("fullstack", "Full Stack Web Development", "sem5", "Node.js, Express, React.js, MongoDB, REST APIs", "", 4, true, 0, 3),
                Subject("compiler", "Compiler Design", "sem5", "Lexical Analysis, Parsing, Syntax-Directed Translation", "", 5, true, 0, 3)
            )
            "sem6" -> listOf(
                Subject("ml", "Machine Learning", "sem6", "Supervised & Unsupervised Learning, Linear Regression, SVM", "", 1, true, 0, 3),
                Subject("cloud", "Cloud Computing & DevOps", "sem6", "AWS basics, Docker, Kubernetes, CI/CD", "", 2, true, 0, 3),
                Subject("cyber", "Cyber Security & Cryptography", "sem6", "Encryption Algorithms, Firewalls, Ethical Hacking", "", 3, true, 0, 3),
                Subject("android", "Android App Development", "sem6", "Kotlin, Jetpack Compose, MVVM, Room, Firebase", "", 4, true, 0, 3),
                Subject("bigdata", "Big Data Analytics", "sem6", "Hadoop, MapReduce, Spark, NoSQL", "", 5, true, 0, 3)
            )
            "sem7" -> listOf(
                Subject("iot", "Internet of Things (IoT)", "sem7", "Arduino, Raspberry Pi, Sensors, MQTT", "", 1, true, 0, 3),
                Subject("block", "Blockchain Technology", "sem7", "Cryptocurrency, Smart Contracts, Ethereum, Solidity", "", 2, true, 0, 3),
                Subject("nlp", "Natural Language Processing", "sem7", "Text Tokenization, Sentiment Analysis, Transformers", "", 3, true, 0, 3),
                Subject("capstone1", "Capstone Project - Phase 1", "sem7", "Project Proposal, Literature Survey, System Design", "", 4, true, 0, 3)
            )
            "sem8" -> listOf(
                Subject("capstone2", "Capstone Project - Phase 2 / Internship", "sem8", "Full Product Development, Testing, Deployment", "", 1, true, 0, 3),
                Subject("prof_ethics", "Professional Ethics & Management", "sem8", "Engineering Ethics, IP rights, Entrepreneurship", "", 2, true, 0, 3)
            )
            else -> listOf(
                Subject("gen1", "General Elective", semesterId, "Elective Course", "", 1, true, 0, 3)
            )
        }
    }

    fun getResourcesForSubject(subjectId: String): List<Resource> {
        return when (subjectId) {
            "ds" -> listOf(
                Resource("r_ds1", subjectId, "Striver A2Z DSA Sheet / Playlist", "Complete Data Structures & Algorithms masterclass by Striver.", "https://www.youtube.com/@takeUforward", "", "takeUforward", "Striver", "Unit 1-4", "All Levels", ResourceType.VIDEO, "", "", true, false),
                Resource("r_ds2", subjectId, "Love Babbar DSA Placement Course", "C++ & Data Structures complete series from scratch.", "https://www.youtube.com/watch?v=WQoB2z6HWqw", "", "Love Babbar", "Love Babbar", "Unit 1", "Beginner", ResourceType.VIDEO, "", "", true, false),
                Resource("r_ds3", subjectId, "GeeksforGeeks Data Structures", "Comprehensive tutorials and practice problems.", "https://www.geeksforgeeks.org/data-structures/", "", "GeeksforGeeks", "", "All Units", "All", ResourceType.WEBSITE, "Tutorial", "", true, false)
            )
            "os" -> listOf(
                Resource("r_os1", subjectId, "Operating Systems Full Course", "Neso Academy complete OS lectures with CPU scheduling and Deadlocks.", "https://www.youtube.com/playlist?list=PLBlnK6fEyqRiVhbXDGLXDk_OQAeuVcp2O", "", "Neso Academy", "Neso Academy", "Unit 1-3", "Beginner", ResourceType.VIDEO, "", "", true, false),
                Resource("r_os2", subjectId, "Gate Smashers Operating Systems", "Quick conceptual clarity for exams and placement interviews.", "https://www.youtube.com/playlist?list=PLxCzCOWd7aiG3SIUMZZZ_3c_9w-Hw_0yX", "", "Gate Smashers", "Gate Smashers", "Unit 2-4", "Intermediate", ResourceType.VIDEO, "", "", true, false)
            )
            "dbms" -> listOf(
                Resource("r_db1", subjectId, "DBMS Complete Course", "Gate Smashers SQL, Normalization, and Transactions masterclass.", "https://www.youtube.com/playlist?list=PLxCzCOWd7aiFAN6I8vVIBuOpJylXYRXzt", "", "Gate Smashers", "Gate Smashers", "Unit 1-4", "Beginner", ResourceType.VIDEO, "", "", true, false),
                Resource("r_db2", subjectId, "Neso Academy DBMS Lectures", "Relational algebra, ER diagrams, and indexing.", "https://www.youtube.com/playlist?list=PLBlnK6fEyqRjrUsVsI8gR8eLp1Y6509f7", "", "Neso Academy", "Neso Academy", "Unit 1-2", "Beginner", ResourceType.VIDEO, "", "", true, false)
            )
            "cn" -> listOf(
                Resource("r_cn1", subjectId, "Computer Networks Full Course", "Neso Academy OSI model, TCP/IP, and IP addressing.", "https://www.youtube.com/playlist?list=PLBlnK6fEyqRgMCU3WIFzT_HEOx7nQZgU", "", "Neso Academy", "Neso Academy", "Unit 1-4", "Beginner", ResourceType.VIDEO, "", "", true, false)
            )
            else -> listOf(
                Resource("r1", subjectId, "Complete Lecture Series", "Detailed topic-by-topic lectures by top educators.", "https://www.youtube.com/@NesoAcademy", "", "Neso Academy", "Neso Academy", "Unit 1", "Beginner", ResourceType.VIDEO, "", "", true, false),
                Resource("r2", subjectId, "Important Exam PYQs", "Previous years' exam questions and solutions.", "https://www.youtube.com/@GateSmashers", "", "Gate Smashers", "Gate Smashers", "Unit 2", "Intermediate", ResourceType.VIDEO, "", "", true, false),
                Resource("r3", subjectId, "GeeksforGeeks Tutorials", "Comprehensive notes and code examples.", "https://www.geeksforgeeks.org/", "", "GeeksforGeeks", "", "All Units", "All", ResourceType.WEBSITE, "Tutorial", "", true, false)
            )
        }
    }

    fun getRecommendedChannels(): List<YouTubeChannel> {
        return listOf(
            YouTubeChannel("c1", "Neso Academy", "Best for core CSE subjects like OS, DBMS, Computer Networks", "", "https://www.youtube.com/@NesoAcademy", listOf("Operating Systems", "Computer Networks", "DBMS")),
            YouTubeChannel("c2", "Gate Smashers", "Simplified concepts for CSE exams and placements", "", "https://www.youtube.com/@GateSmashers", listOf("Data Structures", "DBMS", "Software Engineering")),
            YouTubeChannel("c3", "takeUforward (Striver)", "Best DSA placement preparation & A2Z Sheet", "", "https://www.youtube.com/@takeUforward", listOf("Data Structures & Algorithms", "Dynamic Programming")),
            YouTubeChannel("c4", "Love Babbar", "Complete C++ and Placement series", "", "https://www.youtube.com/@LoveBabbar", listOf("Programming", "Data Structures"))
        )
    }
}
