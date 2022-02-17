package com.romanivanov.benchmark;

import com.romanivanov.benchmark.models.assembly.CollectionsFragmentTests;
import com.romanivanov.benchmark.models.assembly.MapsFragmentTests;
import com.romanivanov.benchmark.ui.MainActivityTest;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;

@RunWith(Suite.class)
@Suite.SuiteClasses({MainActivityTest.class, CollectionsFragmentTests.class, MapsFragmentTests.class})
public class TestSuite {
}
