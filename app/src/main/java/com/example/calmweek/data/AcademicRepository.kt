package com.example.calmweek.data

import com.example.calmweek.model.*


class AcademicRepository {
    fun getSemesters(): List<Semester> {
        return listOf(
            Semester("sem1", "Semester 1", 1, 6, 85),
            Semester("sem2", "Semester 2", 2, 6, 75),
            Semester("sem3", "Semester 3", 3, 7, 60),
            Semester("sem4", "Semester 4", 4, 7, 50),
            Semester("sem5", "Semester 5", 5, 6, 65),
            Semester("sem6", "Semester 6", 6, 6, 30),
            Semester("sem7", "Semester 7", 7, 5, 15),
            Semester("sem8", "Semester 8", 8, 3, 0)
        )
    }

    fun getSubjectsForSemester(semesterId: String): List<Subject> {
        return when (semesterId) {
            "sem1" -> listOf(
                Subject("pps", "Programming for Problem Solving", "sem1", "C Programming, Arrays, Pointers, Structures, File Handling", "", 1, true, 85, 12),
                Subject("math1", "Engineering Mathematics-I", "sem1", "Calculus, Ordinary Differential Equations, Matrices", "", 2, true, 80, 10),
                Subject("phy", "Engineering Physics", "sem1", "Quantum Physics, Wave Optics, Lasers", "", 3, true, 75, 9),
                Subject("bee", "Basic Electrical Engineering", "sem1", "DC/AC Circuits, Transformers, Electrical Machines", "", 4, true, 70, 8),
                Subject("comm", "Communication Skills", "sem1", "Professional communication, Technical writing, Grammar", "", 5, true, 90, 7)
            )
            "sem2" -> listOf(
                Subject("oop_cpp", "Object Oriented Programming in C++", "sem2", "Classes, Objects, Inheritance, Polymorphism, Templates", "", 1, true, 80, 14),
                Subject("math2", "Engineering Mathematics-II", "sem2", "Multiple Integrals, Vector Calculus, Laplace Transforms", "", 2, true, 70, 10),
                Subject("chem", "Engineering Chemistry", "sem2", "Water treatment, Electrochemistry, Polymers, Spectroscopy", "", 3, true, 75, 9),
                Subject("dld", "Digital Logic Design", "sem2", "Boolean Algebra, Logic Gates, Flip-Flops, Counters", "", 4, true, 65, 11),
                Subject("python", "Python Programming", "sem2", "Python basics, Data structures, Libraries, Scripting", "", 5, true, 85, 12)
            )
            "sem3" -> listOf(
                Subject("ds", "Data Structures", "sem3", "Arrays, Stacks, Queues, Linked Lists, Trees, Graphs", "", 1, true, 85, 16),
                Subject("java", "Object Oriented Programming (Java)", "sem3", "Core Java, Collections Framework, Multithreading, JDBC", "", 2, true, 78, 14),
                Subject("co", "Computer Organization & Architecture", "sem3", "Instruction Set Architecture, ALU, Pipeline, Cache Memory", "", 3, true, 60, 10),
                Subject("discrete", "Discrete Mathematics", "sem3", "Sets, Relations, Graph Theory, Combinatorics, Logic", "", 4, true, 55, 9),
                Subject("web_basic", "Web Development Basics", "sem3", "HTML5, CSS3, JavaScript, Responsive Design", "", 5, true, 90, 12)
            )
            "sem4" -> listOf(
                Subject("daa", "Design and Analysis of Algorithms", "sem4", "Sorting, Greedy Algorithms, Dynamic Programming, Graph Algorithms, NP-Completeness", "", 1, true, 65, 15),
                Subject("toc", "Theory of Computation", "sem4", "Automata, Regular Expressions, Context-Free Grammars, Turing Machines", "", 2, true, 50, 11),
                Subject("os", "Operating Systems", "sem4", "Processes, Threads, CPU Scheduling, Deadlocks, Memory Management, File Systems", "", 3, true, 72, 14),
                Subject("se", "Software Engineering", "sem4", "Software Process Models, Agile, Scrum, Testing, UML Diagrams", "", 4, true, 70, 10),
                Subject("java_adv", "Advanced Java & Frameworks", "sem4", "Spring Boot, Servlets, JSP, Hibernate", "", 5, true, 60, 12)
            )
            "sem5" -> listOf(
                Subject("dbms", "Database Management Systems", "sem5", "ER Model, Relational Algebra, SQL, Normalization, Transactions, Indexing", "", 1, true, 68, 14),
                Subject("cn", "Computer Networks", "sem5", "OSI & TCP/IP Models, Routing Protocols, TCP/UDP, DNS, Network Security", "", 2, true, 60, 13),
                Subject("ai", "Artificial Intelligence", "sem5", "State Space Search, Heuristics, Knowledge Representation, Prolog, Minimax", "", 3, true, 55, 11),
                Subject("fullstack", "Full Stack Web Development", "sem5", "Node.js, Express, React.js, MongoDB, REST APIs", "", 4, true, 75, 16),
                Subject("compiler", "Compiler Design", "sem5", "Lexical Analysis, Parsing, Syntax-Directed Translation, Code Generation", "", 5, true, 45, 9)
            )
            "sem6" -> listOf(
                Subject("ml", "Machine Learning", "sem6", "Supervised & Unsupervised Learning, Linear Regression, SVM, Neural Networks", "", 1, true, 40, 12),
                Subject("cloud", "Cloud Computing & DevOps", "sem6", "AWS/GCP basics, Docker, Kubernetes, CI/CD Pipelines", "", 2, true, 50, 10),
                Subject("cyber", "Cyber Security & Cryptography", "sem6", "Encryption Algorithms, Firewalls, Ethical Hacking, Network Defense", "", 3, true, 35, 9),
                Subject("android", "Android App Development", "sem6", "Kotlin, Jetpack Compose, MVVM, Room, Firebase", "", 4, true, 85, 15),
                Subject("bigdata", "Big Data Analytics", "sem6", "Hadoop, MapReduce, Spark, NoSQL Databases", "", 5, true, 30, 8)
            )
            "sem7" -> listOf(
                Subject("iot", "Internet of Things (IoT)", "sem7", "Arduino, Raspberry Pi, Sensors, MQTT Protocol, IoT Cloud", "", 1, true, 20, 8),
                Subject("block", "Blockchain Technology", "sem7", "Cryptocurrency, Smart Contracts, Ethereum, Solidity", "", 2, true, 25, 7),
                Subject("nlp", "Natural Language Processing", "sem7", "Text Tokenization, Sentiment Analysis, Transformers, BERT", "", 3, true, 10, 6),
                Subject("capstone1", "Capstone Project - Phase 1", "sem7", "Project Proposal, Literature Survey, System Design, Prototype", "", 4, true, 50, 5)
            )
            "sem8" -> listOf(
                Subject("capstone2", "Capstone Project - Phase 2 / Internship", "sem8", "Full Product Development, Testing, Deployment, Final Thesis", "", 1, true, 0, 5),
                Subject("prof_ethics", "Professional Ethics & Industrial Management", "sem8", "Engineering Ethics, IP rights, Entrepreneurship", "", 2, true, 0, 4)
            )
            else -> listOf(
                Subject("gen1", "General Elective", semesterId, "Elective Course", "", 1, true, 50, 5)
            )
        }
    }

    fun getResourcesForSubject(subjectId: String): List<Resource> {
        return listOf(
            Resource("r1", subjectId, "Complete Course & Lecture Series", "Detailed topic-by-topic lectures with practical problem solving.", "https://www.youtube.com/watch?v=mPosoHXdxbc", "", "Neso Academy", "Neso Academy", "Unit 1", "Beginner", ResourceType.VIDEO, "", "", true, true),
            Resource("r2", subjectId, "Important Exam Questions & PYQs", "Previous years' exam questions and important derivations explained.", "https://www.youtube.com/watch?v=rruWcgUbcfU", "", "Gate Smashers", "Gate Smashers", "Unit 2", "Intermediate", ResourceType.VIDEO, "", "", true, false),
            Resource("r3", subjectId, "GeeksforGeeks & TutorialsPoint Notes", "Comprehensive tutorials, algorithm explanations, and code examples.", "https://www.geeksforgeeks.org/", "", "GeeksforGeeks", "", "All Units", "All", ResourceType.WEBSITE, "Tutorial", "", true, false),
            Resource("r4", subjectId, "Quick Revision & Formula Summary PDF", "Condensed revision notes for mid-term and end-term examinations.", "", "", "Internal", "", "Unit 1-4", "All", ResourceType.NOTE, "PDF", "https://www.w3.org/WAI/ER/tests/xhtml/testfiles/resources/pdf/dummy.pdf", false, false)
        )
    }

    fun getRecommendedChannels(): List<YouTubeChannel> {
        return listOf(
            YouTubeChannel("c1", "Neso Academy", "Best for core CSE subjects like OS, DBMS, Digital Logic", "", "https://www.youtube.com/@NesoAcademy", listOf("Operating Systems", "Computer Networks", "Digital Logic")),
            YouTubeChannel("c2", "Gate Smashers", "Simplified concepts for CSE exams and GATE preparation", "", "https://www.youtube.com/@GateSmashers", listOf("Data Structures", "DBMS", "Software Engineering")),
            YouTubeChannel("c3", "Jenny's Lectures CS IT", "Clear step-by-step tutorials on programming and algorithms", "", "https://www.youtube.com/@JennysLectures", listOf("Programming Fundamentals", "Data Structures", "Algorithms"))
        )
    }
}
