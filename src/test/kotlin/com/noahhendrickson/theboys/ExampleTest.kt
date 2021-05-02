package com.noahhendrickson.theboys

import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test

internal class ExampleTest {

    @Test
    fun `check if 2 plus 2 equals 4`() {
        assertThat(2 plus 2).isEqualTo(4)
    }

    @Test
    fun `check if 5 plus 5 equals 10`() {
        assertThat(5 plus 5).isEqualTo(10)
    }

    @Test
    fun `check if -1 plus 8 equals 7`() {
        assertThat(-1 plus 8).isEqualTo(7)
    }

    @Test
    fun `check if Integer MAX_VALUE plus Integer MAX_VALUE equals -2`() {
        assertThat(Int.MAX_VALUE plus Int.MAX_VALUE).isEqualTo(-2)
    }

    @Test
    fun `check if Integer MAX_VALUE plus Integer MIN_VALUE equals -1`() {
        assertThat(Int.MAX_VALUE plus Int.MIN_VALUE).isEqualTo(-1)
    }

    private infix fun Int.plus(that: Int) = this + that
}